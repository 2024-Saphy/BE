package saphy.saphy.sales.dto.request;

import lombok.Getter;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;
import saphy.saphy.sales.SalesStatus;
import saphy.saphy.sales.domain.Defect;
import saphy.saphy.sales.domain.Sales;

@Getter
public class SalesCreateRequest {
	private Long itemId;
	private DefectRequest defect;

	public Sales toEntity(Member member, Item item) {
		return Sales.builder()
				.salesStatus(SalesStatus.IN_PROGRESS)
				.defect(defect.toEntity())
				.item(item)
				.member(member)
				.build();
	}

	static class DefectRequest {
		private String display;
		private String appearance;
		private String batteryEfficiency;
		private String function;
		private String purchaseDate;
		private String isRepaired;

		public Defect toEntity() {
			return Defect.of(display, appearance, batteryEfficiency, function, purchaseDate, isRepaired);
		}
	}
}
