package saphy.saphy.item.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.domain.repository.custom.ItemQueryDslRepository;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long>, ItemQueryDslRepository {
	Slice<T> findByDeviceType(DeviceType deviceType, Pageable pageable);
}
