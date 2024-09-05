package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Brand {
	APPLE("Apple"),
	SAMSUNG("Samsung"),
	GOOGLE("Google");

	private final String name;

	public static Brand findByName(String name) {
		return Arrays.stream(Brand.values())
			.filter(brand -> brand.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));
	}
}
