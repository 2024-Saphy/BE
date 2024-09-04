package saphy.saphy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.auth.dto.request.OAuthJoinRequest;
import saphy.saphy.auth.dto.request.OAuthLoginRequest;
import saphy.saphy.auth.domain.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.repository.MemberRepository;
import saphy.saphy.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 이미 등록된 회원인지 확인
    public Boolean isMemberRegistered(OAuthLoginRequest request) {
        return memberRepository.findByEmailAndSocialType(request.getEmail(), request.getSocialType()).isPresent();
    }

    // 회원 가입
    @Transactional
    public void join(OAuthJoinRequest request) {
        Member joinMember = request.toEntity(bCryptPasswordEncoder);
        memberRepository.save(joinMember);
    }
}
