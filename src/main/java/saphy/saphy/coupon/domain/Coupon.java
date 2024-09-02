package saphy.saphy.coupon.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.member.domain.Member;

@Entity
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String discountRate; // 할인 퍼센트  ex:30%

    @Column
    private String discountAmount; // 할인 금액 ex:3000원

    @Enumerated(EnumType.STRING)
    @Column
    private CouponType couponType; // 쿠폰 종류

    @Column
    private double minimumPrice; // 쿠폰 적용이 가능한 최소 주문 금액

    @Column
    private String name; // 쿠폰 이름
    
    @Column
    private String content; // 쿠폰 설명

    @Column
    private LocalDate endAt; // 만료 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
