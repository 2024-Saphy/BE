package saphy.saphy.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.RefreshEntity;
import saphy.saphy.auth.domain.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Value("${spring.jwt.access-token-expiration}")
    private long ACCESS_TOKEN_TTL;

    @Value("${spring.jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_TTL; // 7일

    // Refresh Token이 유효하면 새로운 Access Token 발급
    public void reissueAccessToken(String loginId, HttpServletRequest request, HttpServletResponse response) {
        // Step 1: Refresh Token 추출 및 유효성 검사
        String refreshToken = getRefreshToken(request);

        // Step 2: 기존 Refresh Token 삭제(재사용 불가 목적)
        refreshRepository.deleteById(loginId);

        // Step 3: 새로운 Access Token 및 Refresh Token 발급
        String newAccessToken = jwtUtil.createJwt("access", loginId, ACCESS_TOKEN_TTL);
        String newRefreshToken = jwtUtil.createJwt("refresh", loginId, REFRESH_TOKEN_TTL); // 7일

        // Step 4: 새로운 Refresh Token Redis에 저장
        RefreshEntity refreshEntity = new RefreshEntity(newRefreshToken, loginId);
        refreshRepository.save(refreshEntity);

        // Step 5: 새로운 Refresh Token을 쿠키에 저장하여 반환
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.addHeader("Set-Cookie", createCookie("refresh", newRefreshToken).toString());
    }

    // 쿠키에서 Refresh Token을 추출하고 유효성 검사를 한 번에 수행
    private String getRefreshToken(HttpServletRequest request) {
        // 쿠키에서 refresh token 추출
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> SaphyException.from(ErrorCode.TOKEN_NOT_FOUND)); // 만료된 토큰인 경우

        if (!jwtUtil.validateToken(refreshToken)) {
            throw SaphyException.from(ErrorCode.INVALID_REFRESH_TOKEN); // 유효하지 않은 토큰인 경으
        }
        if (jwtUtil.isExpired(refreshToken)) {
            throw SaphyException.from(ErrorCode.EXPIRED_REFRESH_TOKEN); // 만료된 토큰인 경우
        }

        return refreshToken; // refresh token 반환
    }

    // 새로운 Refresh Token 쿠키로 반환
    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(REFRESH_TOKEN_TTL)
                .build();
    }
}
