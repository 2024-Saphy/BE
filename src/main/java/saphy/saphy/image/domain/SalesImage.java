package saphy.saphy.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.sales.domain.Sales;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class SalesImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_image_id")
	private Long id;

	@Embedded
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sales_id")
	private Sales sales;

	/**
	 * 생성 메서드
	 */
	public static SalesImage createSalesImage(
		String uploadName,
		String storeName,
		String url,
		Sales sales
	) {
		SalesImage salesImage = new SalesImage();

		salesImage.image = Image.of(uploadName, storeName, url);
		salesImage.setSales(sales);

		return salesImage;
	}

	/**
	 * 연관관계 설정 메서드
	 */
	public void setSales(Sales sales) {
		this.sales = sales;
		sales.getImages().add(this);
	}
}
