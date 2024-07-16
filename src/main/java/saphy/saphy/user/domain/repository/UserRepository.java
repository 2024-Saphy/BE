package saphy.saphy.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.user.domain.Member;

public interface UserRepository extends JpaRepository<Member, Long> {

}
