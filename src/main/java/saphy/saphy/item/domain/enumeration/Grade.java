package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
	S("S등급"),
	A("A등급"),
	B("B등급"),
	C("C등급");

	private final String name;

	public static Grade findByName(String name) {
		return Arrays.stream(Grade.values())
			.filter(grade -> grade.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 등급입니다."));
	}
}
