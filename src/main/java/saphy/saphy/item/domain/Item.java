package saphy.saphy.item.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.image.domain.ItemDescriptionImage;
import saphy.saphy.image.domain.ItemImage;
import saphy.saphy.item.domain.enumeration.DeviceType;

@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "deviceType")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "items")
public class Item extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(insertable = false, updatable = false)
	private DeviceType deviceType;

	@Column(name = "name", nullable = false)
	private String name;

	@Lob
	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "stock", nullable = false)
	private int stock;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<ItemImage> images = new ArrayList<>();

	@OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
	private ItemDescriptionImage itemDescriptionImage;

	public boolean canOrder() {
		return stock > 0;
	}

	public void decreaseStock(int i) {
		this.stock = this.stock - i;
	}

	public void update(Item item) {
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.stock = item.getStock();
	}

	public void setItemDescriptionImage(ItemDescriptionImage itemDescriptionImage) {
		this.itemDescriptionImage = itemDescriptionImage;
	}
}
