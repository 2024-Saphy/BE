package saphy.saphy.auth.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import saphy.saphy.auth.domain.repository.RefreshRepository;
import saphy.saphy.auth.domain.RefreshEntity;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(
            AuthenticationManager authenticationManager,
            RefreshRepository refreshRepository,
            JWTUtil jwtUtil,
            String url
    ) {
        this.authenticationManager = authenticationManager;
        this.refreshRepository = refreshRepository;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl(url);
    }

    // 로그인 요청 시 작동
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // request 받은 json 값 추출
        Map<String, String> loginInfo = getLoginInfoFromJson(request);
        String loginId = loginInfo.get("loginId");
        String password = loginInfo.get("password");

        // 해당 값이 없을시 예외처리
        if (loginId == null || password == null) {
            throw SaphyException.from(ErrorCode.INVALID_REQUEST);
        }

        // 스프링 시큐리티에서 username과 password를 검증하기 위해 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password);

        // 검증을 위해 AuthenticationManager로 전달 토큰 전달
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        // 인증에 성공한 loginId 받아오기
        String loginId = authResult.getName();

        // 토큰 생성(스프링 내부 로직에 접근하기 전,
        String accessToken = jwtUtil.createJwt("access", loginId, 20000L);
        String refresh = jwtUtil.createJwt("refresh", loginId, 604800000L);

        // Redis에 refreshToken 저장
        RefreshEntity refreshToken = new RefreshEntity(refresh, loginId);
        refreshRepository.save(refreshToken);

        response.addHeader("Authorization", "Bearer " + accessToken);// 헤더에 access 토큰 넣기
        response.addHeader("Set-Cookie", createCookie("refresh", refresh).toString()); // 쿠키 생성일 추가

        createAPIResponse(response, ErrorCode.REQUEST_OK);
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        createAPIResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path("/") // 쿠키 경로 설정(=도메인 내 모든경로)
                .sameSite("None") // sameSite 설정 (크롬 전용)
//                .httpOnly(false) // JS에서 쿠키 접근 가능하도록함
//                .secure(true) // HTTPS 연결에서만 쿠키 사용 sameSite 설정시 필요
                .maxAge(24 * 60 * 60) // refresh 토큰 만료주기
                .build();
    }

    private void createAPIResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ApiResponse apiResponse = new ApiResponse<>(errorCode);
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), apiResponse);
    }

    // JSON 파일을 읽고 반환하는 메소드
    private Map<String, String> getLoginInfoFromJson(HttpServletRequest request) {
        // 요청의 Content-Type이 "application/json"이 아닌 경우 예외 발생
        if (!"application/json".equals(request.getContentType())) {
            throw SaphyException.from(ErrorCode.INVALID_REQUEST);
        }

        // 요청의 내용을 한 줄씩 읽어와서 StringBuilder로 추가
        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            throw SaphyException.from(ErrorCode.INVALID_REQUEST);
        }

        // 성공시 JSON 문자열을 Map<String, String> 형식으로 변환하여 반환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString.toString(), new TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            throw SaphyException.from(ErrorCode.INVALID_REQUEST);
        }
    }
}
