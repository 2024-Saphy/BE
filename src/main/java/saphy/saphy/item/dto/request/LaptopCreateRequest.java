package saphy.saphy.item.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import saphy.saphy.item.domain.Laptop;
import saphy.saphy.item.domain.enumeration.Brand;
import saphy.saphy.item.domain.enumeration.Color;
import saphy.saphy.item.domain.enumeration.Grade;
import saphy.saphy.item.domain.enumeration.Graphics;
import saphy.saphy.item.domain.enumeration.Memory;
import saphy.saphy.item.domain.enumeration.Processor;
import saphy.saphy.item.domain.enumeration.Storage;

@Getter
public class LaptopCreateRequest {

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
			.deviceType(deviceType)
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
