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
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.dto.request.MemberJoinRequest;
import saphy.saphy.member.dto.request.MemberInfoUpdateRequest;
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
    public void checkLogin(Member member, HttpServletResponse response) {
        if (member == null) {
            throw SaphyException.from(MEMBER_NOT_FOUND);
        }
    }

    // 회원 가입
    @Transactional
    public void join(MemberJoinRequest request) {
        validateExistMember(request);
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        Member member = request.toEntity(encodedPassword);

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
        updateIfPresent(request.getAddress(), member::setAddress);
        updateIfPresent(request.getPhoneNumber(), member::setPhoneNumber);
        updateIfPresent(request.getEmail(), member::setEmail);

        memberRepository.save(member);  // 변경된 내용을 저장합니다.
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(String loginId) {
        Member member = ensureMemberExists(loginId);
        memberRepository.delete(member);
    }

    // 주어진 값(value)이 null이 아닐 경우에만 특정 작업(setter)을 수행하도록 설계된 메서드, null 체크를 간단히 할 수 있음!
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
