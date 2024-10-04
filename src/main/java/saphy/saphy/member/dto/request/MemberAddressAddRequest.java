package saphy.saphy.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberAddressAddRequest {
	@NotNull(message = "주소는 필수 값입니다.")
	private String address;

	@NotNull(message = "상세 주소는 필수 값입니다.")
	private String detailAddress;
}
