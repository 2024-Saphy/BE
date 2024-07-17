package saphy.saphy.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.member.domain.Member;

public interface UserRepository extends JpaRepository<Member, Long> {

}
