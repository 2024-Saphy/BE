package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {
	PHONE("휴대폰"),
	TABLET("태블릿"),
	LAPTOP("노트북");

	private final String name;

	public static DeviceType findByName(String name) {
		return Arrays.stream(DeviceType.values())
			.filter(deviceType -> deviceType.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기종입니다."));
	}
}
