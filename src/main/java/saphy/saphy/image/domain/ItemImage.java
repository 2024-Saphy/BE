package saphy.saphy.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class ItemImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_image_id")
	private Long id;

	@Embedded
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	/**
	 * 생성 메서드
	 */
	public static ItemImage createItemImage(
		String uploadName,
		String storeName,
		String url,
		Item item
	) {
		ItemImage itemImage = new ItemImage();

		itemImage.image = Image.of(uploadName, storeName, url);
		itemImage.setItem(item);

		return itemImage;
	}

	/**
	 * 연관관계 설정 메서드
	 */
	public void setItem(Item item) {
		this.item = item;
		item.getImages().add(this);
	}
}
