package saphy.saphy.item.domain.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.request.SearchParam;

public interface ItemQueryDslRepository {
	Slice<Item> findByDeviceTypeAndSortType(SearchParam searchParam, Pageable pageable);

	Slice<Item> findBySortType(SearchParam searchParam, Pageable pageable);
}
