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

	protected List<ImageResponse> images;

	public static ItemResponse from(Item item) {
		ItemResponse response = new ItemResponse();

		response.id = item.getId();
		response.deviceType = item.getDeviceType().getName();
		response.name = item.getName();
		response.description = item.getDescription();
		response.price = item.getPrice().intValue();
		response.stock = item.getStock();
		response.images = item.getImages().stream()
			.map(itemImage -> ImageResponse.from(itemImage.getImage()))
			.toList();

		return response;
	}
}
