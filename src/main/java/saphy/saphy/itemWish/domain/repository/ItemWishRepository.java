package saphy.saphy.itemWish.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saphy.saphy.item.domain.Item;
import saphy.saphy.itemWish.domain.ItemWish;
import saphy.saphy.member.domain.Member;

import java.util.List;

public interface ItemWishRepository extends JpaRepository<ItemWish, Long> {
    void deleteByMemberAndItem(Member member, Item item);

    @Query("SELECT iw.item FROM ItemWish iw WHERE iw.member = :member")
    List<Item> findItemsByMember(@Param("member") Member member);
}
