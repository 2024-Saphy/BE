package saphy.saphy.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.order.domain.Order;
import saphy.saphy.order.domain.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	long countByStatusAndMember_Id(OrderStatus status, Long id);

}
