package saphy.saphy.item.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParam {
	private String query;

	private String sort = "id";

	private String deviceType = "ALL";

	private String canOrder;

	private String brand;

	private String color;

	private String storage;

	private String grade;
}
