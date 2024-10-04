package saphy.saphy.item.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

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
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.item.dto.request.*;
import saphy.saphy.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository<Item> itemRepository;

	/**
	 * 공통
	 */
	// deviceType 에 따라 각기 다른 응답을 반환하지만, 최종 반환형은 ItemResponse 로 통일. 이후 다시 자식 클래스로 형변환하여 자세한 정보를 전달
	public ItemResponse findById(Long itemId) {
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));

		return item.getDeviceType().mapResponse(item);
	}

	public Slice<ItemResponse> findMainPageItemsByDeviceType(SearchParam searchParam, Pageable pageable) {
		return itemRepository.findByDeviceType(DeviceType.valueOf(searchParam.getDeviceType()), pageable)
			.map(ItemResponse::from);
	}

	public Slice<ItemResponse> searchByDeviceType(SearchParam searchParam, Pageable pageable) {
		return itemRepository.findByDeviceTypeAndSortType(searchParam, pageable)
			.map(ItemResponse::from);
	}

	public void delete(Member member, Long itemId) {
		isAdmin(member);

		itemRepository.deleteById(itemId);
	}

	/**
	 * 휴대폰
	 */
	public Item savePhone(Member member, PhoneCreateRequest request) {
		isAdmin(member);

		Phone phone = request.toEntity();

		return itemRepository.save(phone);
	}
  
  public void updatePhone(Member member, Long itemId, PhoneUpdateRequest request) {
		isAdmin(member);

		Phone findPhone = (Phone) itemRepository.findById(itemId)
				.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));
		Phone updatePhone = request.toEntity();

		findPhone.update(updatePhone);
	}

	/**
	 * 태블릿
	 */
	public Item saveTablet(Member member, TabletCreateRequest request) {
		isAdmin(member);

		Tablet tablet = request.toEntity();

		return itemRepository.save(tablet);
	}

	public void updateTablet(Member member, Long itemId, TabletUpdateRequest request) {
		isAdmin(member);

		Tablet findTablet = (Tablet) itemRepository.findById(itemId)
				.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));
		Tablet updateTablet = request.toEntity();

		findTablet.update(updateTablet);
	}
	/**
	 * 노트북
	 */
	public Item saveLaptop(Member member, LaptopCreateRequest request) {
		isAdmin(member);

		Laptop laptop = request.toEntity();

		return itemRepository.save(laptop);
	}

	public void updateLaptop(Member member, Long itemId, LaptopUpdateRequest request) {
		isAdmin(member);

		Laptop findLaptop = (Laptop) itemRepository.findById(itemId)
				.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));
		Laptop updateLaptop = request.toEntity();

		findLaptop.update(updateLaptop);
	}

	private void isAdmin(Member member) {
		if(!member.getIsAdmin()) {
			throw SaphyException.from(ErrorCode.MEMBER_NOT_ADMIN);
		}
	}
}
