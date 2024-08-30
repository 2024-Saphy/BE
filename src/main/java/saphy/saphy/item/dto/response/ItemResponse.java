package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponse {
	private Long id;

	private String deviceType;

	private String name;

	private String description;

	private String brand;

	private String color;

	private String storage;

	private String grade;

	private int price;

	private int stock;

	public static ItemResponse from(Item item) {
		ItemResponse response = new ItemResponse();

		response.id = item.getId();
		response.deviceType = item.getDeviceType().getName();
		response.name = item.getName();
		response.description = item.getDescription();
		response.brand = item.getBrand().getName();
		response.color = item.getColor().getName();
		response.storage = item.getStorage().getName();
		response.grade = item.getGrade().getName();
		response.price = item.getPrice().intValue();
		response.stock = item.getStock();

		return response;
	}
}
