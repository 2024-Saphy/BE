package saphy.saphy.member.dto.response;

import lombok.Builder;
import lombok.Getter;

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
}
