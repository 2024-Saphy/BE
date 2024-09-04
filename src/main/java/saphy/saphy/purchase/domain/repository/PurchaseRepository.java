package saphy.saphy.purchase.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.PurchaseStatus;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	long countByStatusAndMember_Id(PurchaseStatus status, Long id);

}
