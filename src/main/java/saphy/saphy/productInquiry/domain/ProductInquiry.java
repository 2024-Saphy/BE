package saphy.saphy.productInquiry.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.user.domain.User;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_inquiry")
public class ProductInquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
