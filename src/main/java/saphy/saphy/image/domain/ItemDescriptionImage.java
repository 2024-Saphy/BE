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
public class ItemDescriptionImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "description_image_id")
	private Long id;

	@Embedded
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	/**
	 * 생성 메서드
	 */
	public static ItemDescriptionImage createItemDescriptionImage(
		String uploadName,
		String storeName,
		String url,
		Item item
	) {
		ItemDescriptionImage itemDescriptionImage = new ItemDescriptionImage();

		itemDescriptionImage.image = Image.of(uploadName, storeName, url);
		itemDescriptionImage.setItem(item);

		return itemDescriptionImage;
	}

	/**
	 * 연관관계 설정 메서드
	 */
	public void setItem(Item item) {
		this.item = item;
		item.setItemDescriptionImage(this);
	}
}
