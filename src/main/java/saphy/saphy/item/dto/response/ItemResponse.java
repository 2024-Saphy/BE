package saphy.saphy.item.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.image.dto.response.ImageResponse;
import saphy.saphy.item.domain.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponse {
	protected Long id;

	protected String deviceType;

	protected String name;

	protected String description;

	protected int price;

	protected int stock;

	protected boolean canOrder;

	protected List<ImageResponse> images;

	public ItemResponse(Item item) {
		this.id = item.getId();
		this.deviceType = item.getDeviceType().getName();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice().intValue();
		this.stock = item.getStock();
		this.canOrder = item.canOrder();
		this.images = item.getImages().stream()
			.map(itemImage -> ImageResponse.from(itemImage.getImage()))
			.toList();
	}

	public static ItemResponse from(Item item) {
		return new ItemResponse(item);
	}
}
