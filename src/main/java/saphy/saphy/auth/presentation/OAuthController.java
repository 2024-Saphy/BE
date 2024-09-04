package saphy.saphy.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.auth.dto.request.OAuthJoinRequest;
import saphy.saphy.auth.dto.request.OAuthLoginRequest;
import saphy.saphy.auth.service.OAuthService;
import saphy.saphy.auth.service.SecurityServiceImpl;
import saphy.saphy.auth.service.TokenService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
@Tag(name = "Oauth2", description = "참고: OAuth2 관련 인증은 플러터에서 이루어짐")
public class OAuthController {
    private final OAuthService OAuthService;
    private final TokenService tokenService;
    private final SecurityServiceImpl securityService;

    @PostMapping("/login")
    @Operation(summary = "소셜 로그인 API", description = "회원 O: 로그인 처리 및 토큰 발급 / 회원 X: 300 리다이렉션 처리")
    public ApiResponse<Void> socialLogin(
            @RequestBody @Valid OAuthLoginRequest loginDto,
            Errors errors,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 사용자가 이미 등록된 회원인지 확인
        boolean isRegistered = OAuthService.isMemberRegistered(loginDto);

        if (isRegistered) {
            // 이미 회원인 경우 - 로그인 처리 및 토큰 발급
            tokenService.addTokensToResponse(request, response);
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
