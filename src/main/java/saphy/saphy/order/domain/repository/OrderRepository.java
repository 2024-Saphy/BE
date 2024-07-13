package saphy.saphy.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
