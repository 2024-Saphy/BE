package saphy.saphy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.auth.domain.dto.request.OAuthSignUpDto;
import saphy.saphy.auth.domain.dto.request.OAuthLoginDTO;
import saphy.saphy.auth.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.dto.request.JoinMemberDto;
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

        validateExistMember(joinDto);
        Member joinMember = OAuthSignUpDto.toEntity(joinDto);
        memberRepository.save(joinMember);
    }

    private void validateExistMember(OAuthSignUpDto joinDto) {

        String loginId = joinDto.getEmail();
        if (memberRepository.existsByLoginId(loginId)) {
            throw SaphyException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }
}
