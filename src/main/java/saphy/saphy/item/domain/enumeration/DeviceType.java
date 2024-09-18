package saphy.saphy.item.domain.enumeration;

import java.util.Arrays;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.item.dto.response.LaptopResponse;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.dto.response.TabletResponse;

@Getter
@AllArgsConstructor
public enum DeviceType {
	PHONE("휴대폰", PhoneResponse::from),
	TABLET("태블릿", TabletResponse::from),
	LAPTOP("노트북", LaptopResponse::from);

	private final String name;
	private final Function<Item, ItemResponse> responseMapper;

	// 람다를 사용해 response 생성
	public ItemResponse mapResponse(Item item) {
		return responseMapper.apply(item);
	}

	public static DeviceType findByName(String name) {
		return Arrays.stream(DeviceType.values())
			.filter(deviceType -> deviceType.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기종입니다."));
	}
}
