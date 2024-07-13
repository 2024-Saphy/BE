package saphy.saphy.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.delivery.domain.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
