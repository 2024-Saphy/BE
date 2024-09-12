package saphy.saphy.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Laptop;
import saphy.saphy.item.domain.Phone;
import saphy.saphy.item.domain.Tablet;
import saphy.saphy.item.dto.request.LaptopCreateRequest;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.request.TabletCreateRequest;
import saphy.saphy.item.dto.response.LaptopResponse;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.dto.response.TabletResponse;
import saphy.saphy.item.repository.ItemRepository;
import saphy.saphy.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository<Item> itemRepository;

	/**
	 * 휴대폰
	 */
	public void savePhone(PhoneCreateRequest request) {
		Phone phone = request.toEntity();
		itemRepository.save(phone);
	}

	public PhoneResponse findPhoneById(Long itemId) {
		return itemRepository.findById(itemId)
			.map(PhoneResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 휴대폰입니다."));
	}

	public List<PhoneResponse> findAllPhones() {
		return itemRepository.findAll().stream()
			.map(PhoneResponse::from)
			.toList();
	}

	/**
	 * 태블릿
	 */
	public void saveTablet(TabletCreateRequest request) {
		Tablet tablet = request.toEntity();
		itemRepository.save(tablet);
	}

	public TabletResponse findTabletById(Long itemId) {
		return itemRepository.findById(itemId)
			.map(TabletResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 태블릿입니다."));
	}

	public List<TabletResponse> findAllTablets() {
		return itemRepository.findAll().stream()
			.map(TabletResponse::from)
			.toList();
	}

	/**
	 * 노트북
	 */
	public void saveLaptop(LaptopCreateRequest request) {
		Laptop laptop = request.toEntity();
		itemRepository.save(laptop);
	}

	public LaptopResponse findLaptopById(Long itemId) {
		return itemRepository.findById(itemId)
			.map(LaptopResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노트북입니다."));
	}

	public List<LaptopResponse> findAllLaptops() {
		return itemRepository.findAll().stream()
			.map(LaptopResponse::from)
			.toList();
	}

	public void delete(Member member, Long itemId) {
		if(!member.getIsAdmin()){
			throw SaphyException.from(ErrorCode.MEMBER_NOT_ADMIN);
		}
		itemRepository.deleteById(itemId);
	}
}
