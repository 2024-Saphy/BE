package saphy.saphy.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.auth.service.ReissueService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reissue")
@Tag(name = "ReissueController", description = "토큰 재발급 API")
public class ReissueController {
    private final ReissueService reissueService;

    @PostMapping
    @Operation(summary = "토큰 재발급 API", description = "access token이 만료되면 새로운 토큰을 발급받습니다.")
    ApiResponse<Void> reissue(@AuthenticationPrincipal CustomUserDetails member, HttpServletRequest request, HttpServletResponse response) {

        reissueService.reissueAccessToken(member.getMember().getLoginId(), request, response);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
