package saphy.saphy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.dto.request.SocialLoginDTO;
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

    // 이미 등록된 회원인지 확인
    public Boolean isMemberRegistered(SocialLoginDTO socialLoginDTO) {
        return memberRepository.findByEmailAndSocialType(socialLoginDTO.getEmail(), socialLoginDTO.getSocialType()).isPresent();
    }
}
