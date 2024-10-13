package saphy.saphy.image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.image.domain.ItemDescriptionImage;

public interface ItemDescriptionImageRepository extends JpaRepository<ItemDescriptionImage, Long> {
	Optional<ItemDescriptionImage> findByImage_StoreName(String storeName);

	Optional<ItemDescriptionImage> findByItemId(Long itemId);
}
