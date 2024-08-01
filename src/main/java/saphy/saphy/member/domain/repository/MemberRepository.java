package saphy.saphy.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);

    Optional<Object> findByEmailAndSocialType(String email, SocialType socialType);
}
