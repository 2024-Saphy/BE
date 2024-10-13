package saphy.saphy.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);

    Optional<Object> findByEmailAndSocialType(String email, SocialType socialType);

    Optional<Member> findByLoginIdAndSocialType(String loginId, SocialType socialType);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.profileImage WHERE m.id = :memberId")
    Optional<Member> findWithImagesById(@Param("memberId") Long memberId);
}
