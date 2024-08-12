package saphy.saphy.salesHistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saphy.saphy.salesHistory.SalesStatus;
import saphy.saphy.salesHistory.domain.SalesHistory;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {

    //판매 상태의 개수를 조회하는 메소드
    // 멤버 ID와 상태별로 구매 내역의 개수를 조회하는 메소드
    @Query("SELECT COUNT(sh) FROM SalesHistory sh WHERE sh.salesStatus = :status AND sh.member.id = :memberId")
    long countByPurchaseStatusAndMemberId(@Param("status") SalesStatus status, @Param("memberId") Long memberId);

}
