package saphy.saphy.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.auth.domain.dto.request.OAuthSignUpDto;
import saphy.saphy.auth.domain.dto.request.OAuthLoginDTO;
import saphy.saphy.auth.service.OAuthService;
import saphy.saphy.auth.service.SecurityService;
import saphy.saphy.auth.service.SecurityServiceImpl;
import saphy.saphy.auth.service.TokenService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService OAuthService;
    private final TokenService tokenService;
    private final SecurityServiceImpl securityService;

    @PostMapping("/login")
    public ApiResponse<Void> socialLogin(@RequestBody @Valid OAuthLoginDTO loginDto, Errors errors,
                                         HttpServletRequest request, HttpServletResponse response) {

        validateRequest(errors);

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
    public ApiResponse<Void> join(@RequestBody @Valid OAuthSignUpDto joinDto, Errors errors,
                                  HttpServletRequest request, HttpServletResponse response) {

        validateRequest(errors);
        OAuthService.join(joinDto);
        securityService.saveUserInSecurityContext(joinDto);
        tokenService.addTokensToResponse(request, response);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    private void validateRequest(Errors errors) {

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                throw SaphyException.from(ErrorCode.INVALID_REQUEST);
            });
        }
    }
}
