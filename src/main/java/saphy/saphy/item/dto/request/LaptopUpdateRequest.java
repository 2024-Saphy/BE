package saphy.saphy.item.dto.request;

import lombok.Getter;
import saphy.saphy.item.domain.Laptop;
import saphy.saphy.item.domain.enumeration.*;

import java.math.BigDecimal;

@Getter
public class LaptopUpdateRequest {

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

	public Laptop toEntity() {
		return Laptop.builder()
			.deviceType(DeviceType.findByName(deviceType))
			.name(name)
			.description(description)
			.brand(Brand.findByName(brand))
			.color(Color.findByName(color))
			.storage(Storage.findByName(storage))
			.processor(Processor.findByName(processor))
			.memory(Memory.findByName(memory))
			.graphics(Graphics.findByName(graphics))
			.grade(Grade.findByName(grade))
			.price(BigDecimal.valueOf(price))
			.stock(stock)
			.build();
	}
}
