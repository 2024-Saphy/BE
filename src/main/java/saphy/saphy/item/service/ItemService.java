package saphy.saphy.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.request.ItemCreateRequest;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.item.repository.ItemRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;

	public void save(ItemCreateRequest request) {
		Item phone = request.toEntity();
		itemRepository.save(phone);
	}

	public ItemResponse findById(Long itemId) {
		return itemRepository.findById(itemId)
			.map(ItemResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴대폰입니다."));
	}

	public List<ItemResponse> findAll() {
		return itemRepository.findAll().stream()
			.map(ItemResponse::from)
			.toList();
	}
}
