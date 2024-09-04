package saphy.saphy.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.repository.MemberRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

        // 헤더에서 토큰 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        }

        // 토큰이 없다면 다음 필터로 넘김
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 이하 토큰 검증 실패시 다음 필터로 넘기지 않음
        // 올바르지 않은 토큰(=토큰 검증 실패) 인지 확인
        if (!jwtUtil.validateToken(accessToken)) {
            createAPIResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
            return;
        }
        // 토큰 만료 여부 확인
        if (jwtUtil.isExpired(accessToken)) {
            createAPIResponse(response, ErrorCode.EXPIRED_AUTH_TOKEN);
            return;
        }
        // access 토큰인지 확인
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            createAPIResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
            return;
        }

        // 토큰에서 유저 아이디 정보 추출
        String loginId = jwtUtil.getUsername(accessToken);
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        // 해당 아이디로 가입한 유저가 존재하는지 확인
        if (member.isEmpty()) {
            createAPIResponse(response, ErrorCode.MEMBER_NOT_FOUND);
            return;
        }

        // 찾은 유저 정보로 UserDetails 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(member.get());

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // SecurityContextHolder 에 사용자 등록 (=인가 절차 완료)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

    private void createAPIResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ApiResponse apiResponse = new ApiResponse<>(errorCode);
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), apiResponse);
    }
}
