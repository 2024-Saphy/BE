package saphy.saphy.item.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Laptop;
import saphy.saphy.item.domain.Phone;
import saphy.saphy.item.domain.Tablet;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.dto.request.LaptopCreateRequest;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.request.TabletCreateRequest;
import saphy.saphy.item.dto.response.ItemResponse;
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
	public Item savePhone(PhoneCreateRequest request) {
		Phone phone = request.toEntity();

		return itemRepository.save(phone);
	}

	// deviceType 에 따라 각기 다른 응답을 반환하지만, 최종 반환형은 ItemResponse 로 통일. 이후 다시 자식 클래스로 형변환하여 자세한 정보를 전달
	public ItemResponse findItemById(Long itemId) {
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));
		DeviceType deviceType = item.getDeviceType();

		return createResponseByDeviceType(deviceType, item);
	}

	public List<PhoneResponse> findAllPhones() {
		return itemRepository.findAll().stream()
			.map(PhoneResponse::from)
			.toList();
	}

	/**
	 * 태블릿
	 */
	public Item saveTablet(TabletCreateRequest request) {
		Tablet tablet = request.toEntity();

		return itemRepository.save(tablet);
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
	public Item saveLaptop(LaptopCreateRequest request) {
		Laptop laptop = request.toEntity();

		return itemRepository.save(laptop);
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
		if (!member.getIsAdmin()) {
			throw SaphyException.from(ErrorCode.MEMBER_NOT_ADMIN);
		}
		itemRepository.deleteById(itemId);
	}

	// 이거 분명 더 깔끔하게 할 수 있을 것 같은데... 일단은 이렇게 구현해놓고 나중에 고쳐보겠습니다
	private @NotNull ItemResponse createResponseByDeviceType(DeviceType deviceType, Item item) {
		if (deviceType.equals(DeviceType.PHONE)) {
			return PhoneResponse.from(item);
		} else if (deviceType.equals(DeviceType.TABLET)) {
			return TabletResponse.from(item);
		} else if (deviceType.equals(DeviceType.LAPTOP)) {
			return LaptopResponse.from(item);
		} else {
			throw SaphyException.from(ErrorCode.INVALID_DEVICE_TYPE);
		}
	}
}
