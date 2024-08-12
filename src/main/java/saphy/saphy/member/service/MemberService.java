package saphy.saphy.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.auth.utils.AccessTokenUtils;
import saphy.saphy.delivery.domain.DeliveryStatus;
import saphy.saphy.delivery.service.DeliveryService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.dto.request.JoinMemberDto;
import saphy.saphy.member.domain.dto.response.MemberInfoDto;
import saphy.saphy.member.domain.repository.MemberRepository;
import saphy.saphy.purchaseHistory.domain.PurchaseStatus;
import saphy.saphy.purchaseHistory.service.PurchaseHistoryService;
import saphy.saphy.salesHistory.SalesStatus;
import saphy.saphy.salesHistory.service.SalesHistoryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DeliveryService deliveryService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final SalesHistoryService salesHistoryService;

    // 회원 가입
    @Transactional
    public void join(JoinMemberDto joinDto) {

        validateExistMember(joinDto);
        Member joinMember = JoinMemberDto.toEntity(joinDto);
        memberRepository.save(joinMember);
    }


    private void validateExistMember(JoinMemberDto joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw SaphyException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }

    // 회원 정보 조회
    public MemberInfoDto getInfo() {
        String loginId = AccessTokenUtils.isPermission();
        Member findMember = memberRepository.findByLoginId(loginId).orElse(null);

        Map<DeliveryStatus, Long> deliveryCounts = deliveryService.getDeliveryStatusCounts(findMember.getId());
        Map<PurchaseStatus, Long> purchaseCounts = purchaseHistoryService.getPurchaseCounts(findMember.getId());
        Map<SalesStatus, Long> salesCounts = salesHistoryService.getPurchaseCounts(findMember.getId());

        return MemberInfoDto.builder()
                .nickname(findMember.getNickName())
                //.profileImgUrl(findMember.getProfileImgUrl)
                .deliveryStartedCount(deliveryCounts.get(DeliveryStatus.STARTED))
                .deliveryGoingCount(deliveryCounts.get(DeliveryStatus.GOING))
                .deliveryDeliveredCount(deliveryCounts.get(DeliveryStatus.DELIVERED))
                .purchasePendingCount(purchaseCounts.get(PurchaseStatus.PENDING))
                .purchaseInProgressCount(purchaseCounts.get(PurchaseStatus.IN_PROGRESS))
                .purchaseCompletedCount(purchaseCounts.get(PurchaseStatus.COMPLETED))
                .salesPendingCount(salesCounts.get(SalesStatus.PENDING))
                .salesInProgressCount(salesCounts.get(SalesStatus.IN_PROGRESS))
                .salesCompletedCount(salesCounts.get(SalesStatus.COMPLETED))
                .build();
    }

}
