package saphy.saphy.salesHistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.salesHistory.domain.SalesHistory;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
}
