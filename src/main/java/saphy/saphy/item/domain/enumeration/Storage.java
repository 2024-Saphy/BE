package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Storage {
	STORAGE_128("128GB"),
	STORAGE_256("256GB"),
	STORAGE_512("512GB"),
	STORAGE_1024("1TB");

	private final String name;

	public static Storage findByName(String name) {
		return Arrays.stream(Storage.values())
			.filter(storage -> storage.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저장 용량입니다."));
	}
}
