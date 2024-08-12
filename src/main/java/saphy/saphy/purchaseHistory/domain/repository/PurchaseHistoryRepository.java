package saphy.saphy.purchaseHistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saphy.saphy.purchaseHistory.domain.PurchaseHistory;
import saphy.saphy.purchaseHistory.domain.PurchaseStatus;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    //구매 상태의 개수를 조회하는 메소드
    @Query("SELECT COUNT(ph) FROM PurchaseHistory ph WHERE ph.purchaseStatus = :status AND ph.member.id = :memberId")
    long countByPurchaseStatusAndMemberId(@Param("status") PurchaseStatus status, @Param("memberId") Long memberId);
}
