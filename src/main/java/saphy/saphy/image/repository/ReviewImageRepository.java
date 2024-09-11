package saphy.saphy.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saphy.saphy.image.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
	@Query("select r from ReviewImage r where r.id = ?1")
	List<ReviewImage> findByReviewId(Long id);
}
