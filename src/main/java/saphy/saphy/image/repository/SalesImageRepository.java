package saphy.saphy.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saphy.saphy.image.domain.ItemImage;
import saphy.saphy.image.domain.SalesImage;

public interface SalesImageRepository extends JpaRepository<SalesImage, Long> {
	@Query("select s from SalesImage s where s.sales.id= ?1")
	List<SalesImage> findBySalesId(Long id);
}
