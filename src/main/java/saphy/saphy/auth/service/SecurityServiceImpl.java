package saphy.saphy.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.auth.domain.dto.request.OAuthJoinRequest;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;
import saphy.saphy.member.domain.repository.MemberRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    private SecurityServiceImpl(MemberRepository memberRepository, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    // 유저 정보 추출
    @Override
    public void saveUserInSecurityContext(OAuthJoinRequest socialLoginDTO) {

        String socialId = socialLoginDTO.getEmail();
        String socialProvider = socialLoginDTO.getSocialType().name();

        authenticateMember(socialId, SocialType.valueOf(socialProvider));
    }

    // 기능: 시큐리티 컨텍스트에 소셜로그인 사용자 저장(인증)
    // SecurityConfig에서 /oauth2 경로를 ignore하면 시큐리티 필터 체인을 안거침
    // 이로 인해 시큐리티 컨텍스트에 사용자를 저장(인증)하지 못하게 되어 구현
    private void authenticateMember(String socialId, SocialType socialProvider) {

        UserDetails userDetails = loadUserBySocialIdAndSocialProvider(socialId, socialProvider);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        // 추후 관리자 권한 생길 시 조건문 처리 필요
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 주어진 소셜 ID와 소셜 제공자를 기준으로 사용자 정보 조회(실질적 동작 메소드)
    private UserDetails loadUserBySocialIdAndSocialProvider(String socialId, SocialType socialProvider) {
        Optional<Member> member = memberRepository.findByLoginIdAndSocialType(socialId, socialProvider);

        if (member.isEmpty()) {
            throw SaphyException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        } else {
            return new CustomUserDetails(member.get());
        }
    }
}