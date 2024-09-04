package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Graphics {
	RTX_2060("RTX 2060"),
	RTX_2070("RTX 2070"),
	RTX_2080("RTX 2080"),
	RTX_2080TI("RTX 2080 Ti"),
	RTX_3060("RTX 3060"),
	RTX_3070("RTX 3070"),
	RTX_3080("RTX 3080"),
	RTX_3090("RTX 3090"),
	RTX_4050("RTX 4050"),
	RTX_4070("RTX 4070"),
	DEFAULT("기본"),
	ETC("기타");

	private final String name;

	public static Graphics findByName(String name) {
		return Arrays.stream(Graphics.values())
			.filter(graphics -> graphics.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그래픽 카드입니다."));
	}
}
