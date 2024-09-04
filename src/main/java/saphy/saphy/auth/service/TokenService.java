package saphy.saphy.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;

    public void addTokensToResponse(HttpServletRequest request, HttpServletResponse response) {
        // request 객체에서 loginId를 가져온다고 가정
        String loginId = request.getParameter("email");

        // 토큰 발급
        Map<String, String> tokenMap = jwtUtil.initToken(loginId);

        // 응답 헤더에 토큰 삽입
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenMap.get("accessToken"));
        response.setHeader("Set-Cookie", tokenMap.get("refreshToken"));
    }

    // 응답 바디와 함께 헤더에 토큰 삽입
    public ErrorCode getResponseWithTokens(HttpServletRequest request) {
        // request 객체에서 loginId를 가져온다고 가정
        String loginId = request.getParameter("email");

        // 토큰 발급
        Map<String, String> tokenMap = jwtUtil.initToken(loginId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + tokenMap.get("accessToken"));
        headers.set("Set-Cookie", tokenMap.get("refreshToken"));

        // 바디와 함께 응답 반환
        return ErrorCode.REQUEST_OK;
    }
}
