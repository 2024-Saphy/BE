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

import java.util.Optional;

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
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        // Step 1: Refresh Token 유효성 검사
        String refreshToken = getRefreshToken(request);
        validateRefreshToken(refreshToken);

        // Step 2: Redis에 저장된 Refresh Token과 비교하여 사용자 ID 가져오기
        String loginId = validateAndGetUserId(refreshToken);

        // Step 3: 기존 Refresh Token 삭제(재사용 불가 목적)
        refreshRepository.deleteById(refreshToken);

        // Step 4: 새로운 Access Token 및 Refresh Token 발급
        String newAccessToken = jwtUtil.createJwt("access", loginId, ACCESS_TOKEN_TTL);
        String newRefreshToken = jwtUtil.createJwt("refresh", loginId, REFRESH_TOKEN_TTL); // 7일

        // Step 5: 새로운 Refresh Token Redis에 저장
        RefreshEntity refreshEntity = new RefreshEntity(newRefreshToken, loginId);
        refreshRepository.save(refreshEntity);

        // Step 6: 새로운 Refresh Token을 쿠키에 저장하여 반환
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.addHeader("Set-Cookie", createCookie("refresh", newRefreshToken).toString());
    }

    // 쿠키에서 Refresh Token을 추출
    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw SaphyException.from(ErrorCode.TOKEN_NOT_FOUND); // refresh token 없으면 예외처리
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                return cookie.getValue();
            }
        }
        throw SaphyException.from(ErrorCode.TOKEN_NOT_FOUND);
    }

    // Refresh Token이 유효한지 확인하는 메서드
    private void validateRefreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw SaphyException.from(ErrorCode.INVALID_REFRESH_TOKEN); // 유효하지 않으면 예외 발생
        }
        if (jwtUtil.isExpired(refreshToken)) {
            throw SaphyException.from(ErrorCode.EXPIRED_REFRESH_TOKEN); // 만료된 토큰일 경우 예외 발생
        }
    }

    // Redis에 저장된 Refresh Token과 비교하고 사용자 ID 반환
    private String validateAndGetUserId(String refreshToken) {
        Optional<RefreshEntity> refreshTokenEntity = refreshRepository.findById(refreshToken);
        if (refreshTokenEntity.isEmpty()) {
            throw SaphyException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return refreshTokenEntity.get().getLoginId();
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
