package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Phone;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneResponse {
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

	public static PhoneResponse from(Phone phone) {
		PhoneResponse response = new PhoneResponse();

		response.id = phone.getId();
		response.deviceType = phone.getDeviceType();
		response.name = phone.getName();
		response.description = phone.getDescription();
		response.brand = phone.getBrand().getName();
		response.color = phone.getColor().getName();
		response.storage = phone.getStorage().getName();
		response.grade = phone.getGrade().getName();
		response.price = phone.getPrice().intValue();
		response.stock = phone.getStock();

		return response;
	}
}
