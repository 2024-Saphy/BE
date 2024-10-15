package saphy.saphy.member.service;

import static saphy.saphy.global.exception.ErrorCode.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.image.repository.ProfileImageRepository;
import saphy.saphy.member.domain.Account;
import saphy.saphy.member.domain.Address;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.dto.request.MemberAccountAddRequest;
import saphy.saphy.member.dto.request.MemberAccountUpdateRequest;
import saphy.saphy.member.dto.request.MemberAddressAddRequest;
import saphy.saphy.member.dto.request.MemberAddressUpdateRequest;
import saphy.saphy.member.dto.request.MemberJoinRequest;
import saphy.saphy.member.dto.request.MemberInfoUpdateRequest;
import saphy.saphy.member.dto.response.MemberAccountResponse;
import saphy.saphy.member.dto.response.MemberAddressResponse;
import saphy.saphy.member.dto.response.MemberDetailResponse;
import saphy.saphy.member.dto.response.MemberInfoResponse;
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
    public void isCurrentMember(String loginId) {
        // 현재 로그인된 사용자의 정보를 가져와서 비교
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoginId = ((CustomUserDetails) authentication.getPrincipal()).getMember().getLoginId();

        if (!loginId.equals(currentLoginId))
            throw SaphyException.from(ErrorCode.MEMBER_NOT_AUTHENTICATED);
    }

    // 회원 가입
    @Transactional
    public void join(MemberJoinRequest request) {
        validateExistMember(request);
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        Member member = request.toEntity(encodedPassword);
        ProfileImage defaultProfileImage = ProfileImage.createProfileImage(
            "default_profile_image.png",
            "default_profile_image.png",
            "https://w7.pngwing.com/pngs/665/132/png-transparent-user-defult-avatar.png",
                member
            );
        profileImageRepository.save(defaultProfileImage);
        member.updateProfileImage(defaultProfileImage);

        memberRepository.save(member);
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
    public MemberInfoResponse getInfo(Member member) {
        Map<PurchaseStatus, Long> purchaseCounts = purchaseService.getPurchaseCounts(member.getId());
        Map<SalesStatus, Long> salesCounts = salesService.getPurchaseCounts(member.getId());

        return MemberInfoResponse.toDto(member, purchaseCounts, salesCounts);
    }

    // 회원 정보 수정
    @Transactional
    public void updateMemberInfo(Member member, MemberInfoUpdateRequest request) {
        updateIfPresent(request.getPassword(), member::setPassword);
        updateIfPresent(request.getName(), member::setName);
        updateIfPresent(request.getNickName(), member::setNickName);
        updateIfPresent(request.getPhoneNumber(), member::setPhoneNumber);
        updateIfPresent(request.getEmail(), member::setEmail);

        memberRepository.save(member);  // 변경된 내용을 저장합니다.
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    // 회원 탈퇴 전 검증 메서드
    public void validateMemberForDeletion(Member member) {
        ensureMemberExists(member);
        isCurrentMember(member.getLoginId());
        // 관리자 관련 로직 추가될 수 있음
    }

    // 주어진 값(value)이 null이 아닐 경우에만 특정 작업(setter)을 수행하도록 설계된 메서드, null 체크를 간단히 할 수 있음!
    private <T> void updateIfPresent(T value, java.util.function.Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    // 회원 존재 유무 검증(member 반환)
    private Member ensureMemberExists(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> SaphyException.from(MEMBER_NOT_FOUND));
    }

    // 회원 존재 유무 검증만
    private void ensureMemberExists(Member member) {
        if (!memberRepository.existsByLoginId(member.getLoginId())) {
            throw SaphyException.from(MEMBER_NOT_FOUND);
        }
    }

    // 중복회원 검증
    private void validateExistMember(MemberJoinRequest joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw SaphyException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }

    /**
     * 회원 주소
     */
    public MemberAddressResponse findMemberAddress(Member member) {
        return MemberAddressResponse.toDto(member);
    }

    @Transactional
    public void addMemberAddress(Member member, MemberAddressAddRequest request) {
        Address address = Address.of(request.getAddress(), request.getDetailAddress());
        member.addAddress(address);
    }

    @Transactional
    public void updateMemberAddress(Member member, MemberAddressUpdateRequest request) {
        Address address = Address.of(request.getAddress(), request.getDetailAddress());
        member.updateAddress(address);
    }

    @Transactional
    public void deleteMemberAddress(Member member) {
        member.removeAddress();
    }

    /**
     * 계좌
     */
    public MemberAccountResponse findMemberAccount(Member member) {
        return MemberAccountResponse.toDto(member);
    }

    @Transactional
    public void addMemberAccount(Member member, MemberAccountAddRequest request) {
        Account account = Account.of(request.getBankName(), request.getAccountNumber());
        member.addAccount(account);
    }

    @Transactional
    public void updateMemberAccount(Member member, MemberAccountUpdateRequest request) {
        Account account = Account.of(request.getBankName(), request.getAccountNumber());
        member.updateAccount(account);
    }

    @Transactional
    public void deleteMemberAccount(Member member) {
        member.removeAccount();
    }
}
