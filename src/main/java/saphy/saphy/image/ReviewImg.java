package saphy.saphy.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.review.domain.Review;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review_img")
public class ReviewImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
