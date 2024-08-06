package saphy.saphy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.auth.domain.dto.request.OAuthSignUpDto;
import saphy.saphy.auth.domain.dto.request.OAuthLoginDTO;
import saphy.saphy.auth.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
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

    // 이미 등록된 회원인지 확인
    public Boolean isMemberRegistered(OAuthLoginDTO OAuthLoginDTO) {
        return memberRepository.findByEmailAndSocialType(OAuthLoginDTO.getEmail(), OAuthLoginDTO.getSocialType()).isPresent();
    }

    // 회원 가입
    @Transactional
    public void join(OAuthSignUpDto joinDto) {

        // 회원이 존재하는 경우 로그인 처리하므로 중복 회원 검사 X
        Member joinMember = OAuthSignUpDto.toEntity(joinDto);
        memberRepository.save(joinMember);
    }
}
