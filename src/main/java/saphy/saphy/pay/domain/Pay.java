package saphy.saphy.pay.domain;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.experimental.SuperBuilder;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Pay extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "merchant_id", unique = true)
    private String merchantUid;

    @Column(name = "imp_uid", unique = true)
    private String impUid;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PayMethod payMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PayStatus status;

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }

    public void setStatus(PayStatus status) {
        this.status = status;
    }

}