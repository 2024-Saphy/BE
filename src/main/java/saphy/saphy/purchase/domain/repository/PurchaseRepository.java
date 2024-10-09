package saphy.saphy.purchase.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saphy.saphy.member.domain.Member;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.PurchaseStatus;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	long countByStatusAndMember_Id(PurchaseStatus status, Long id);

	List<Purchase> findByMember(Member member);

	List<Purchase> findByStatusAndMember(PurchaseStatus status, Member member);

	Purchase findByIdAndMember(Long id, Member member);

	@Query("SELECT p FROM Purchase p WHERE p.status IN (:statuses) AND p.member = :member")
	List<Purchase> findByStatusesAndMember(@Param("statuses") List<PurchaseStatus> statuses, @Param("member") Member member);

}
