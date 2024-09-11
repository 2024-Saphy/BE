package saphy.saphy.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saphy.saphy.image.domain.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
	@Query("select i from ItemImage i where i.item.id = ?1")
	List<ItemImage> findByItemId(Long id);
}
