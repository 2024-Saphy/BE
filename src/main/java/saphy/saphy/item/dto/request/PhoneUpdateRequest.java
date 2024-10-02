package saphy.saphy.item.dto.request;

import lombok.Getter;
import saphy.saphy.item.domain.Phone;
import saphy.saphy.item.domain.enumeration.*;

import java.math.BigDecimal;

@Getter
public class PhoneUpdateRequest {

	private String deviceType;

	private String name;

	private String description;

	private String brand;

	private String color;

	private String storage;

	private String grade;

	private int price;

	private int stock;

	public Phone toEntity() {
		return Phone.builder()
			.deviceType(DeviceType.findByName(deviceType))
			.name(name)
			.description(description)
			.brand(Brand.findByName(brand))
			.color(Color.findByName(color))
			.storage(Storage.findByName(storage))
			.grade(Grade.findByName(grade))
			.price(BigDecimal.valueOf(price))
			.stock(stock)
			.build();
	}
}
