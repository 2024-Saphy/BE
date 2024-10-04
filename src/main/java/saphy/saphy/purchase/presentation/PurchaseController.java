package saphy.saphy.purchase.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.member.domain.Member;
import saphy.saphy.purchase.dto.response.PurchaseResponse;
import saphy.saphy.purchase.service.PurchaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
@Tag(name = "PurchaseController", description = "구매 관련 API")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping
    @Operation(summary = "구매 현황 전체 조회 API", description = "사용자의 모든 구매 현황을 조회하는 API 입니다.")
    public ApiResponse<PurchaseResponse> findAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Member loggedInmember = customUserDetails.getMember();
        return new ApiResponse<>(purchaseService.findAll(loggedInmember));
    }
}
