package saphy.saphy.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saphy.saphy.delivery.domain.Delivery;
import org.springframework.data.repository.query.Param;
import saphy.saphy.delivery.domain.DeliveryStatus;


public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    //배달 상태의 개수를 조회하는 메소드
    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.deliveryStatus = :status AND d.order.member.id = :memberId")
    long countByDeliveryStatusAndMemberId(@Param("status") DeliveryStatus status, @Param("memberId") Long memberId);
}
