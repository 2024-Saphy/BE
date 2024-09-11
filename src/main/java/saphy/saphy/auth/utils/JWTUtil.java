package saphy.saphy.auth.utils;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JWTUtil(
            @Value("${spring.jwt.secret-key}") String secretKey,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256
                .key()
                .build()
                .getAlgorithm()
        );
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    // claim 에서 loginId 정보 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("loginId", String.class);
    }

    // claim 에서 토큰 종류 정보 추출
    public String getCategory(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("category", String.class);
    }

    // claim 에서 토큰 만료 여부 추출
    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    // JWT 토큰 생성 Claim 에는 토큰 종류와 loginId 만 담음
    public String createJwt(String category, String loginId, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("loginId", loginId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    // 소셜로그인 토큰 발급
    public void generateToken(Authentication authResult, HttpServletResponse response) {
        // 권한 가져오기
        String authorities = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String loginId = authResult.getName();

        // access Token 생성
        String accessToken = Jwts.builder()
                .claim("category", "access")
                .claim("loginId", loginId)
                .claim("auth", authorities)
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .claim("category", "refresh")
                .claim("loginId", loginId)
                .claim("auth", authorities)
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(secretKey)
                .compact();

        // 헤더에 삽입
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Set-Cookie", createCookie("refresh", refreshToken).toString());
    }

    // 토큰 검증 로직
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 소셜로그인 토큰 발급용
    public Map<String, String> initToken(String loginId) {
        String accessToken = createJwt("access", loginId, accessTokenExpiration);
        String refreshToken = createJwt("refresh", loginId, refreshTokenExpiration);
        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
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
}
