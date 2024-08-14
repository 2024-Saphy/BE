package saphy.saphy.purchaseHistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.purchaseHistory.domain.PurchaseStatus;
import saphy.saphy.purchaseHistory.domain.repository.PurchaseHistoryRepository;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public Map<PurchaseStatus, Long> getPurchaseCounts(Long memberId) {
        Map<PurchaseStatus, Long> statusCounts = new EnumMap<>(PurchaseStatus.class);

        for (PurchaseStatus status : PurchaseStatus.values()) {
            long count = purchaseHistoryRepository.countByPurchaseStatusAndMemberId(status, memberId);
            statusCounts.put(status, count);
        }

        return statusCounts;
    }

}
