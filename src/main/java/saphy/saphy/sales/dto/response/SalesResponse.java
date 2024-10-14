package saphy.saphy.sales.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.image.dto.response.ImageResponse;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.member.dto.response.MemberDetailResponse;
import saphy.saphy.sales.domain.Defect;
import saphy.saphy.sales.domain.Sales;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesResponse {
	private Long id;
	private ItemResponse item;
	private DefectResponse defect;
	private List<ImageResponse> images;
	private MemberDetailResponse member;

	public static SalesResponse toDto(Sales sales) {
		SalesResponse salesResponse = new SalesResponse();

		salesResponse.id = sales.getId();
		salesResponse.item = ItemResponse.from(sales.getItem());
		salesResponse.defect = DefectResponse.toDto(sales.getDefect());
		salesResponse.images = sales.getImages().stream()
			.map(image -> ImageResponse.from(image.getImage()))
			.toList();
		salesResponse.member = MemberDetailResponse.toDto(sales.getMember());

		return salesResponse;
	}

	@Getter
	static class DefectResponse {
		private String display;
		private String appearance;
		private String batteryEfficiency;
		private String function;
		private String purchaseDate;
		private String isRepaired;

		public static DefectResponse toDto(Defect defect) {
			DefectResponse defectResponse = new DefectResponse();

			defectResponse.display = defect.getDisplay();
			defectResponse.appearance = defect.getAppearance();
			defectResponse.batteryEfficiency = defect.getBatteryEfficiency();
			defectResponse.function = defect.getFunction();
			defectResponse.purchaseDate = defect.getPurchaseDate();
			defectResponse.isRepaired = defect.getIsRepaired();

			return defectResponse;
		}
	}
}
