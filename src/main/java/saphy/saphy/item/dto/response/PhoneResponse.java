package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.image.domain.Image;
import saphy.saphy.image.dto.response.ImageResponse;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Phone;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneResponse extends ItemResponse {
	private String brand;

	private String color;

	private String storage;

	private String grade;

	protected ImageResponse descriptionImage;

	public static PhoneResponse from(Item item) {
		PhoneResponse response = new PhoneResponse();

		Phone phone = (Phone) item;
		response.id = phone.getId();
		response.deviceType = phone.getDeviceType().getName();
		response.name = phone.getName();
		response.description = phone.getDescription();
		response.brand = phone.getBrand().getName();
		response.color = phone.getColor().getName();
		response.storage = phone.getStorage().getName();
		response.grade = phone.getGrade().name();
		response.price = phone.getPrice().intValue();
		response.stock = phone.getStock();
		response.images = item.getImages().stream()
			.map(itemImage -> ImageResponse.from(itemImage.getImage()))
			.toList();
		response.descriptionImage = ImageResponse.from(Image.of("1", "1", "https://www.frisbeekorea.com/webfile/webedit/202409/20240910174241_w32uchi6j2.jpg"));

		return response;
	}
}
