package saphy.saphy.purchaseHistory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.order.domain.Order;
import saphy.saphy.user.domain.User;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "purchase_history")
public class PurchaseHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY) //여기 애매하긴 한데, 일단 1:1관계로 생각함.
    @JoinColumn(name = "order_id")
    private Order order;
}
