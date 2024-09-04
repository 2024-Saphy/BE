package saphy.saphy.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public String createNewAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = getRefreshTokenFromCookies(request); // 쿠키에서 토큰 추출
        String loginId = validateAndGetUserEmail(refreshToken); // 추출한 토큰 검증 및 유저반환

        //토큰 생성
        String newAccess = jwtUtil.createJwt("access", loginId, 30 * 60 * 1000L);
        String newRefresh = jwtUtil.createJwt("refresh", "fakeLoginId", 24 * 60 * 60 * 1000L);

        // 새로운 refresh 토큰 쿠키에 삽입
        response.addHeader("Set-Cookie", createCookie("refresh", newRefresh).toString());

        return newAccess;
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        // 쿠키에서 리프레쉬 토큰을 찾아옴
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw SaphyException.from(ErrorCode.TOKEN_NOT_FOUND);
        }
        return refresh;
    }

    private String validateAndGetUserEmail(String refreshToken) {
        //유효하지 않은 토큰 예외처리
        if (!jwtUtil.validateToken(refreshToken)) {
            throw SaphyException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        //refresh 토큰 만료시 예외처리
        if (jwtUtil.isExpired(refreshToken)) {
            throw SaphyException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        //페이로드에 refresh 토큰이 아니면 예외처리 (ex access token)
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            throw SaphyException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        // 리프레시 토큰의 loginId 반환 - 추후 redis 추가시 수정 필요
        return jwtUtil.getUsername(refreshToken);
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path("/") //쿠키 경로 설정(=도메인 내 모든경로)
                .sameSite("None") //sameSite 설정 (크롬)
                .httpOnly(false) //JS에서 쿠키 접근 가능하도록함
                .secure(true) // HTTPS 연결에서만 쿠키 사용 sameSite 설정시 필요
                .maxAge(24 * 60 * 60)// refresh 토큰 만료주기
                .build();
    }
}
