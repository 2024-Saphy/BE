package saphy.saphy.purchase.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;

import java.time.LocalDateTime;
import saphy.saphy.payment.domain.Payment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "purchases")
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status;

    @Column(nullable = false, name = "total_price")
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_type")
    private PaymentType paymentType; // 결제 기능 구현하면서 살릴지 죽일지 알아서 판단

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

   // @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
   // private Payment payment;
}
