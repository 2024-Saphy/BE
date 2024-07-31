package saphy.saphy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.dto.request.KakaoMemberCheckDto;
import saphy.saphy.auth.domain.dto.request.KakaoSignUpDto;
import saphy.saphy.auth.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.repository.MemberRepository;
import saphy.saphy.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class KaKaoService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    // 이미 등록된 회원인지 확인 - true: 로그인 / false: 회원가입 요청 응답
    public Boolean isMemberRegistered(KakaoMemberCheckDto kakaoMemberCheckDto) {
        return memberRepository.findByEmailAndSocialType(kakaoMemberCheckDto.getEmail(), kakaoMemberCheckDto.getSocialType()).isPresent();
    }

    // 새 회원 등록
    public void joinKakao(KakaoSignUpDto request) {

        // 나머진 null로 가입
        Member member = Member.builder()
                .loginId(request.getEmail())
                .email(request.getEmail())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .socialType(request.getSocialType())
                .build();

        memberRepository.save(member);
    }
}
