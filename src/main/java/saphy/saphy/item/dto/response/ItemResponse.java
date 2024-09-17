package saphy.saphy.item.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.image.dto.response.ImageResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // ItemResponse는 상속을 위한 클래스로, 그 자체로 사용되지 않기 떄문에 생성자를 private로 설정한다.
public class ItemResponse {
	protected Long id;

	protected String deviceType;

	protected String name;

	protected String description;

	protected int price;

	protected int stock;

	protected List<ImageResponse> images;
}
