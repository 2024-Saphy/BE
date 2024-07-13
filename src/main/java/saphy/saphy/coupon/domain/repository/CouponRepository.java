package saphy.saphy.coupon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
