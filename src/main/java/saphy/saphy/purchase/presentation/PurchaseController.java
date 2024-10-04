package saphy.saphy.purchase.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "구매 현황 목록 조회 API",
            description = "사용자의 모든 or 상태에 맞는 구매 현황 목록을 조회하는 API 입니다. " +
                    "/ ALL, PENDING, PROCESSING, START, SHIPPED, DELIVERED, CANCELLED"
    )
    public ApiResponse<PurchaseResponse> findAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("status") String status
    ) {
        Member loggedInmember = customUserDetails.getMember();
        if(status.equals("ALL")) {
            return new ApiResponse<>(purchaseService.findAll(loggedInmember));
        }
        return new ApiResponse<>(purchaseService.findByStatus(loggedInmember,status));
    }

    @GetMapping("/{purchaseId}")
    @Operation(summary = "구매 현황 단건 조회 API", description = "사용자의 구매 현황을 단건으로 조회하는 API 입니다.")
    public ApiResponse<PurchaseResponse> findById(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long purchaseId
    ) {
        Member loggedInmember = customUserDetails.getMember();
        return new ApiResponse<>(purchaseService.findById(loggedInmember, purchaseId));
    }
}
