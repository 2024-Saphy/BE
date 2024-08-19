package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Device;
import saphy.saphy.item.domain.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceResponse {
	private Long id;

	private String modelName;

	private String brand;

	private Long capacity;

	private String color;

	private String grade;

	private Item item;

	public static DeviceResponse from(Device device) {
		DeviceResponse response = new DeviceResponse();

		response.id = device.getId();
		response.modelName = device.getModelName();
		response.brand = device.getBrand();
		response.capacity = device.getCapacity();
		response.color = device.getColor();
		response.grade = device.getGrade();
		response.item = device.getItem();

		return response;
	}
}
