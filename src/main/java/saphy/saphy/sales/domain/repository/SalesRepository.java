package saphy.saphy.sales.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saphy.saphy.sales.SalesStatus;
import saphy.saphy.sales.domain.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    //판매 상태의 개수를 조회하는 메소드
    // 멤버 ID와 상태별로 구매 내역의 개수를 조회하는 메소드
    @Query("SELECT COUNT(sh) FROM Sales sh WHERE sh.salesStatus = :status AND sh.member.id = :memberId")
    long countByPurchaseStatusAndMemberId(@Param("status") SalesStatus status, @Param("memberId") Long memberId);

    @Query("select s from Sales s where s.member.id = ?1")
    List<Sales> findByMemberId(Long id);

}
