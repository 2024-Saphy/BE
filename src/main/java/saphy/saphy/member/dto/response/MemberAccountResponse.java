package saphy.saphy.member.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccountResponse {
	private String bankName;
	private String accountNumber;

	public static MemberAccountResponse toDto(String bankName, String accountNumber) {
		MemberAccountResponse memberAccountResponse = new MemberAccountResponse();

		memberAccountResponse.bankName = bankName;
		memberAccountResponse.accountNumber = accountNumber;

		return memberAccountResponse;
	}
}
