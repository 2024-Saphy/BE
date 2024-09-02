package saphy.saphy.order.service;

import java.util.EnumMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import saphy.saphy.order.domain.OrderStatus;
import saphy.saphy.order.domain.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final OrderRepository orderRepository;

	public Map<OrderStatus, Long> getOrderCounts(Long memberId) {
		Map<OrderStatus, Long> statusCounts = new EnumMap<>(OrderStatus.class);

		for (OrderStatus status : OrderStatus.values()) {
			long count = orderRepository.countByStatusAndMember_Id(status, memberId);
			statusCounts.put(status, count);
		}

		return statusCounts;
	}

}
