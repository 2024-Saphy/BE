package saphy.saphy.sales.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.sales.SalesStatus;
import saphy.saphy.sales.domain.repository.SalesRepository;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;

    public Map<SalesStatus, Long> getPurchaseCounts(Long memberId) {
        Map<SalesStatus, Long> statusCounts = new EnumMap<>(SalesStatus.class);

        for (SalesStatus status : SalesStatus.values()) {
            long count = salesRepository.countByPurchaseStatusAndMemberId(status, memberId);
            statusCounts.put(status, count);
        }

        return statusCounts;
    }

}
