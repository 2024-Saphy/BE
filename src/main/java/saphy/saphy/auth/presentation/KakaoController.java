package saphy.saphy.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.auth.domain.dto.request.SocialLoginDTO;
import saphy.saphy.auth.domain.dto.request.KakaoSignUpDto;
import saphy.saphy.auth.service.KaKaoService;
import saphy.saphy.auth.service.TokenService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequestMapping("/oauth2/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KaKaoService kaKaoService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ApiResponse<Void> socialLogin(@RequestBody @Valid SocialLoginDTO kakaoDto, Errors errors,
                                         HttpServletRequest request, HttpServletResponse response) {

        validateRequest(errors);

        // 사용자가 이미 등록된 회원인지 확인
        boolean isRegistered = kaKaoService.isMemberRegistered(kakaoDto);

        if (isRegistered) {
            // 이미 회원인 경우 - 로그인 처리 및 토큰 발급
            tokenService.addTokensToResponse(request, response);
            return new ApiResponse<>(ErrorCode.REQUEST_OK);
        } else {
            // 회원이 아닌 경우 - 300 Multiple Choices 리다이렉션 처리
            return new ApiResponse<>(ErrorCode.MEMBER_JOIN_REQUIRED);
        }
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
