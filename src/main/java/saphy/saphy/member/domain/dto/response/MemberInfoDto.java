package saphy.saphy.member.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoDto {
    private String nickname;
    private String profileImgUrl;

    //배달 상태 개수들
    private Long deliveryStartedCount;
    private Long deliveryGoingCount;
    private Long deliveryDeliveredCount;

    //구매 상태 개수들
    private Long purchasePendingCount;
    private Long purchaseInProgressCount;
    private Long purchaseCompletedCount;

    //판매 상태 개수들
    private Long salesPendingCount;
    private Long salesInProgressCount;
    private Long salesCompletedCount;
}
