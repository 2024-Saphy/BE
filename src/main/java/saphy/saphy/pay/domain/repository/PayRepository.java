package saphy.saphy.pay.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.pay.domain.Pay;

public interface PayRepository extends JpaRepository<Pay, Long> {

}
