package saphy.saphy.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.auth.dto.request.OAuthJoinRequest;
import saphy.saphy.auth.dto.request.OAuthLoginRequest;
import saphy.saphy.auth.service.OAuthService;
import saphy.saphy.auth.service.SecurityServiceImpl;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
@Tag(name = "OauthController", description = "OAuth 관련 API, 참고로 OAuth2 관련 인증은 플러터에서 이루어짐")
public class OAuthController {
    private final JWTUtil jwtUtil;
    private final OAuthService OAuthService;
    private final SecurityServiceImpl securityService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    @Operation(summary = "소셜 로그인 API", description = "회원 O: 로그인 처리 및 토큰 발급 / 회원 X: 300 리다이렉션 처리")
    public ApiResponse<Void> socialLogin(
            @RequestBody @Valid OAuthLoginRequest loginDto,
            HttpServletResponse response
    ) {
        // 사용자가 이미 등록된 회원인지 확인
        boolean isRegistered = OAuthService.isMemberRegistered(loginDto);

        if (isRegistered) {
            // 이미 회원인 경우 - 로그인 처리 및 토큰 발급

            // 1. 카카오 계정 이메일을 기반으로 Authentication 객체 생성
            // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), "default");

            // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
            // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            jwtUtil.generateToken(authentication, response);

            return new ApiResponse<>(ErrorCode.REQUEST_OK);
        } else {
            // 회원이 아닌 경우 - 300 Multiple Choices 리다이렉션 처리
            return new ApiResponse<>(ErrorCode.MEMBER_JOIN_REQUIRED);
        }
    }

    @PostMapping("/join")
    @Operation(summary = "소셜 회원가입 API", description = "소셜 회원가입은 일반 회원가입보다 적은 정보 입력으로 가입이 가능합니다.")
    public ApiResponse<Void> join(
            @RequestBody @Valid OAuthJoinRequest joinDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        OAuthService.join(joinDto);
        securityService.saveUserInSecurityContext(joinDto);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
