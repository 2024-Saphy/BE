package saphy.saphy.item.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.Tablet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TabletResponse extends ItemResponse{
	private String brand;

	private String color;

	private String storage;

	private String grade;

	public static TabletResponse from(Item item) {
		TabletResponse response = new TabletResponse();

		Tablet tablet = (Tablet) item;
		response.id = tablet.getId();
		response.deviceType = tablet.getDeviceType().getName();
		response.name = tablet.getName();
		response.description = tablet.getDescription();
		response.brand = tablet.getBrand().getName();
		response.color = tablet.getColor().getName();
		response.storage = tablet.getStorage().getName();
		response.grade = tablet.getGrade().getName();
		response.price = tablet.getPrice().intValue();
		response.stock = tablet.getStock();

		return response;
	}
}
