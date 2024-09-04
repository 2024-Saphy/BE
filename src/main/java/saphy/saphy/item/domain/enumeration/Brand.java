package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Brand {
	APPLE("애플"),
	SAMSUNG("삼성"),
	GOOGLE("구글");

	private final String name;

	public static Brand findByName(String name) {
		return Arrays.stream(Brand.values())
			.filter(brand -> brand.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));
	}
}
