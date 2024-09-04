package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
	BLACK("블랙"),
	WHITE("화이트"),
	GRAY("그레이"),
	SILVER("실버"),
	GOLD("골드"),
	ROSE_GOLD("로즈골드"),
	RED("레드"),
	BLUE("블루"),
	GREEN("그린"),
	PURPLE("퍼플"),
	YELLOW("옐로우"),
	PINK("핑크"),
	ORANGE("오렌지"),
	BROWN("브라운"),
	BEIGE("베이지"),
	ETC("기타");

	private final String name;

	public static Color findByName(String name) {
		return Arrays.stream(Color.values())
			.filter(color -> color.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 색상입니다."));
	}
}
