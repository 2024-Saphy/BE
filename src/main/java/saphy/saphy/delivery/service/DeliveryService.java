package saphy.saphy.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.delivery.domain.DeliveryStatus;
import saphy.saphy.delivery.domain.repository.DeliveryRepository;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public Map<DeliveryStatus, Long> getDeliveryStatusCounts(Long memberId) {
        Map<DeliveryStatus, Long> statusCounts = new EnumMap<>(DeliveryStatus.class);

        for (DeliveryStatus status : DeliveryStatus.values()) {
            long count = deliveryRepository.countByDeliveryStatusAndMemberId(status, memberId);
            statusCounts.put(status, count);
        }

        return statusCounts;
    }
}
