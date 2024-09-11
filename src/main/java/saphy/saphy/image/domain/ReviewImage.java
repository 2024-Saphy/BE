package saphy.saphy.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.review.domain.Review;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class ReviewImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_image_id")
	private Long id;

	@Embedded
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	/**
	 * 생성 메서드
	 */
	public static ReviewImage createReviewImage(
		String uploadName,
		String storeName,
		String url,
		Review review
	) {
		ReviewImage reviewImage = new ReviewImage();

		reviewImage.image = Image.of(uploadName, storeName, url);
		reviewImage.review = review;

		return reviewImage;
	}

	/**
	 * 연관관계 설정 메서드
	 */
	public void setReview(Review review) {
		this.review = review;
		review.getImages().add(this);
	}
}
