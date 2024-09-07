package saphy.saphy.itemWish.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saphy.saphy.item.domain.Item;
import saphy.saphy.itemWish.domain.ItemWish;
import saphy.saphy.member.domain.Member;

public interface ItemWishRepository extends JpaRepository<ItemWish, Long> {
    void deleteByMemberAndItem(Member member, Item item);
}
