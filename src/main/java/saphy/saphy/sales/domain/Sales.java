package saphy.saphy.sales.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.member.domain.Member;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.sales.SalesStatus;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sales")
public class Sales extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY) //여기 애매하긴 한데, 일단 1:1관계로 생각함.
    @JoinColumn(name = "order_id") // 여기 이름 바꿔야 함
    private Purchase purchase;
}
