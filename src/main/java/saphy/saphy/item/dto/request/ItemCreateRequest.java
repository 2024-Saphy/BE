package saphy.saphy.item.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.Brand;
import saphy.saphy.item.domain.enumeration.Color;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.domain.enumeration.Grade;
import saphy.saphy.item.domain.enumeration.Storage;

@Getter
public class ItemCreateRequest {

	private String deviceType;

	private String name;

	private String description;

	private String brand;

	private String color;

	private String storage;

	private String grade;

	private int price;

	private int stock;

	public Item toEntity() {
		return Item.builder()
			.deviceType(DeviceType.PHONE)
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
