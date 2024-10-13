package saphy.saphy.item.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.domain.repository.custom.ItemQueryDslRepository;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long>, ItemQueryDslRepository {
	Slice<T> findByDeviceType(DeviceType deviceType, Pageable pageable);

	@Query("SELECT i FROM Item i LEFT JOIN FETCH i.itemDescriptionImage WHERE i.id = :itemId")
	Optional<T> findWithImagesById(@Param("itemId") Long itemId);
}
