package saphy.saphy.itemWish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Phone;
import saphy.saphy.item.domain.Tablet;
import saphy.saphy.item.repository.ItemRepository;
import saphy.saphy.itemWish.domain.ItemWish;
import saphy.saphy.itemWish.domain.repository.ItemWishRepository;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.repository.MemberRepository;

import java.util.Optional;

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
}
