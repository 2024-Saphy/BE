package saphy.saphy.item.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
