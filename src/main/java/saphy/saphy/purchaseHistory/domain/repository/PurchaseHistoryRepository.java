package saphy.saphy.purchaseHistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.purchaseHistory.domain.PurchaseHistory;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
}
