package saphy.saphy.salesHistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.salesHistory.SalesStatus;
import saphy.saphy.salesHistory.domain.repository.SalesHistoryRepository;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesHistoryService {

    private final SalesHistoryRepository salesHistoryRepository;

    public Map<SalesStatus, Long> getPurchaseCounts(Long memberId) {
        Map<SalesStatus, Long> statusCounts = new EnumMap<>(SalesStatus.class);

        for (SalesStatus status : SalesStatus.values()) {
            long count = salesHistoryRepository.countByPurchaseStatusAndMemberId(status, memberId);
            statusCounts.put(status, count);
        }

        return statusCounts;
    }

}
