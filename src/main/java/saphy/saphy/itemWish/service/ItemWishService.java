package saphy.saphy.itemWish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.itemWish.domain.ItemWish;
import saphy.saphy.itemWish.domain.repository.ItemWishRepository;
import saphy.saphy.member.domain.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemWishService {
    private final ItemWishRepository itemWishRepository;
    private final ItemRepository<Item> itemRepository;

    @Transactional
    public void save(Member member, Long itemId) {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));

        itemWishRepository.save(ItemWish.builder()
                        .member(member)
                        .item(findItem)
                        .build());
    }

    @Transactional
    public void delete(Member member, Long itemId) {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));

        itemWishRepository.deleteByMemberAndItem(member, findItem);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findAllItemWishes(Member member) {
        return itemWishRepository.findItemsByMember(member).stream()
                .map(ItemResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findByDeviceType(Member member, String deviceType) {
        return itemWishRepository.findItemsByMember(member).stream()
                .filter(item -> item.getDeviceType() == DeviceType.valueOf(deviceType))
                .map(ItemResponse::from)
                .toList();
    }
}
