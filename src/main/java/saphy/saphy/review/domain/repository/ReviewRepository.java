package saphy.saphy.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
