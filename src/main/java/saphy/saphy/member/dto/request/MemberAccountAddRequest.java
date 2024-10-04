package saphy.saphy.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberAccountAddRequest {
	@NotNull(message = "은행명은 필수 값입니다.")
	private String bankName;

	@NotNull(message = "계좌번호는 필수 값입니다.")
	private String accountNumber;
}
