package saphy.saphy.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.item.domain.Item;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
}
