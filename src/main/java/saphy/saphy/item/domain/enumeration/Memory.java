package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Memory {
	MEMORY_8GB("8GB"),
	MEMORY_16GB("16GB"),
	MEMORY_32GB("32GB");

	private final String name;

	public static Memory findByName(String name) {
		return Arrays.stream(Memory.values())
			.filter(memory -> memory.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메모리입니다."));
	}
}
