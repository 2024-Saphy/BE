package saphy.saphy.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import saphy.saphy.member.domain.Member;
import saphy.saphy.purchase.domain.PurchaseStatus;
import saphy.saphy.sales.SalesStatus;

import java.util.Map;

@Getter
@Builder
public class MemberInfoResponse {
    private String nickname;
    private String profileImgUrl;

    //구매 상태 개수들
    private Long purchasePendingCount;
    private Long purchaseInProgressCount;
    private Long purchaseCompletedCount;
    private Long deliveryStartedCount;
    private Long deliveryGoingCount;
    private Long deliveryDeliveredCount;

    //판매 상태 개수들
    private Long salesPendingCount;
    private Long salesInProgressCount;
    private Long salesCompletedCount;

    public static MemberInfoResponse toDto(Member member, Map<PurchaseStatus, Long> purchaseCounts, Map<SalesStatus, Long> salesCounts) {
                return MemberInfoResponse.builder()
                        .nickname(member.getNickName())
                        //.profileImgUrl(findMember.getProfileImgUrl)
                        .deliveryStartedCount(purchaseCounts.get(PurchaseStatus.START))
                        .deliveryGoingCount(purchaseCounts.get(PurchaseStatus.SHIPPED))
                        .deliveryDeliveredCount(purchaseCounts.get(PurchaseStatus.DELIVERED))
                        .purchasePendingCount(purchaseCounts.get(PurchaseStatus.PENDING))
                        .purchaseInProgressCount(purchaseCounts.get(PurchaseStatus.PROCESSING))
                        .purchaseCompletedCount(purchaseCounts.get(PurchaseStatus.DELIVERED))
                        .salesPendingCount(salesCounts.get(SalesStatus.PENDING))
                        .salesInProgressCount(salesCounts.get(SalesStatus.IN_PROGRESS))
                        .salesCompletedCount(salesCounts.get(SalesStatus.COMPLETED))
                        .build();
    }
}
