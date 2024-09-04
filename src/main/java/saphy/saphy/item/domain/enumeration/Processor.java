package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Processor {
	INTEL_CORE_I3("인텔 코어 i3"),
	INTEL_CORE_I5("인텔 코어 i5"),
	INTEL_CORE_I7("인텔 코어 i7"),
	INTEL_CORE_I9("인텔 코어 i9"),
	AMD_RYZEN_3("AMD 라이젠 3"),
	AMD_RYZEN_5("AMD 라이젠 5"),
	AMD_RYZEN_7("AMD 라이젠 7"),
	AMD_RYZEN_9("AMD 라이젠 9");

	private final String name;

	public static Processor findByName(String name) {
		return Arrays.stream(Processor.values())
			.filter(processor -> processor.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로세서입니다."));
	}
}
