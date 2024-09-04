package saphy.saphy.member.service;

import static saphy.saphy.global.exception.ErrorCode.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.utils.AccessTokenUtils;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;
import saphy.saphy.member.domain.dto.request.MemberJoinRequest;
import saphy.saphy.member.domain.dto.request.MemberInfoUpdateDto;
import saphy.saphy.member.domain.dto.response.MemberDetailResponse;
import saphy.saphy.member.domain.dto.response.MemberInfoDto;
import saphy.saphy.member.domain.repository.MemberRepository;
import saphy.saphy.purchase.domain.PurchaseStatus;
import saphy.saphy.purchase.service.PurchaseService;
import saphy.saphy.sales.SalesStatus;
import saphy.saphy.sales.service.SalesService;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PurchaseService purchaseService;
    private final SalesService salesService;

    // 로그인된 회원 체크
    public void checkLogin(Member member, HttpServletResponse response) {
        if (member == null) {
            throw SaphyException.from(MEMBER_NOT_FOUND);
        }
    }

    // 회원 가입
    @Transactional
    public void join(MemberJoinRequest joinDto) {

        validateExistMember(joinDto);
        Member joinMember = Member.builder()
                .loginId(joinDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .socialType(SocialType.LOCAL)
                .name(joinDto.getName())
                .nickName(joinDto.getNickName())
                .address(joinDto.getAddress())
                .phoneNumber(joinDto.getPhoneNumber())
                .email(joinDto.getEmail())
                .isAdmin(Boolean.FALSE)
                .build();
        memberRepository.save(joinMember);
    }

    // 단일 회원 조회
    @Transactional(readOnly = true)
    public MemberDetailResponse getMemberDetails(String loginId) {

        Member member = ensureMemberExists(loginId);
        return MemberDetailResponse.toDto(member);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberDetailResponse> getAllMemberDetails() {

        return memberRepository
                .findAll()
                .stream()
                .map(MemberDetailResponse::toDto)
                .collect(Collectors.toList());
    }

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public MemberInfoDto getInfo(Member loggedInMember) {
        Map<PurchaseStatus, Long> purchaseCounts = purchaseService.getPurchaseCounts(loggedInMember.getId());
        Map<SalesStatus, Long> salesCounts = salesService.getPurchaseCounts(loggedInMember.getId());

        return MemberInfoDto.builder()
                .nickname(loggedInMember.getNickName())
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

    // 회원 정보 수정
    @Transactional
    public void updateMemberInfo(MemberInfoUpdateDto updateDto) {

        String loginId = AccessTokenUtils.isPermission();
        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> SaphyException.from(ErrorCode.MEMBER_NOT_FOUND));


        updateIfPresent(updateDto.getPassword(), findMember::setPassword);
        updateIfPresent(updateDto.getName(), findMember::setName);
        updateIfPresent(updateDto.getNickName(), findMember::setNickName);
        updateIfPresent(updateDto.getAddress(), findMember::setAddress);
        updateIfPresent(updateDto.getPhoneNumber(), findMember::setPhoneNumber);
        updateIfPresent(updateDto.getEmail(), findMember::setEmail);

        memberRepository.save(findMember);  // 변경된 내용을 저장합니다.
    }

    //주어진 값(value)이 null이 아닐 경우에만 특정 작업(setter)을 수행하도록 설계된 메서드, null 체크를 간단히 할 수 있음!
    private <T> void updateIfPresent(T value, java.util.function.Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    // 회원 존재 유무 검증
    private Member ensureMemberExists(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> SaphyException.from(MEMBER_NOT_FOUND));
    }

    // 중복회원 검증
    private void validateExistMember(MemberJoinRequest joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw SaphyException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }
}
