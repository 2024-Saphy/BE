package saphy.saphy.sales.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Defect {
	private String display;
	private String appearance;
	private String batteryEfficiency;
	private String function;
	private String purchaseDate;
	private String isRepaired;

	public static Defect of(String display, String appearance, String batteryEfficiency, String function, String purchaseDate, String isRepaired) {
		Defect defect = new Defect();

		defect.display = display;
		defect.appearance = appearance;
		defect.batteryEfficiency = batteryEfficiency;
		defect.function = function;
		defect.purchaseDate = purchaseDate;
		defect.isRepaired = isRepaired;

		return defect;
	}
}
