package saphy.saphy.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Phone;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.repository.ItemRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository<Item> itemRepository;

	public void save(PhoneCreateRequest request) {
		Item phone = request.toEntity();
		itemRepository.save(phone);
	}

	public PhoneResponse findPhoneById(Long itemId) {
		return itemRepository.findById(itemId)
			.map(Phone.class::cast)
			.map(PhoneResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴대폰입니다."));
	}

	public List<PhoneResponse> findAllPhones() {
		return itemRepository.findAll().stream()
			.map(Phone.class::cast)
			.map(PhoneResponse::from)
			.toList();
	}
}
