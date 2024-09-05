package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Laptop;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LaptopResponse {
	private Long id;

	private String deviceType;

	private String name;

	private String description;

	private String brand;

	private String color;

	private String storage;

	private String processor;

	private String memory;

	private String graphics;

	private String grade;

	private int price;

	private int stock;

	public static LaptopResponse from(Item item) {
		LaptopResponse response = new LaptopResponse();

		Laptop laptop = (Laptop) item;
		response.id = laptop.getId();
		response.deviceType = laptop.getDeviceType();
		response.name = laptop.getName();
		response.description = laptop.getDescription();
		response.brand = laptop.getBrand().getName();
		response.color = laptop.getColor().getName();
		response.storage = laptop.getStorage().getName();
		response.processor = laptop.getProcessor().getName();
		response.memory = laptop.getMemory().getName();
		response.graphics = laptop.getGraphics().getName();
		response.grade = laptop.getGrade().getName();
		response.price = laptop.getPrice().intValue();
		response.stock = laptop.getStock();

		return response;
	}
}
