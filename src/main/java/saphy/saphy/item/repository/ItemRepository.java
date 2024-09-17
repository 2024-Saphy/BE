package saphy.saphy.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.DeviceType;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
	List<T> findByDeviceType(DeviceType deviceType);

}
