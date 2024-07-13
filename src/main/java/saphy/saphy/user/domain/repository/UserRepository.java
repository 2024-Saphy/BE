package saphy.saphy.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
