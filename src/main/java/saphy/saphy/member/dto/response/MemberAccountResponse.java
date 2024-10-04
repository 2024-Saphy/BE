package saphy.saphy.member.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.member.domain.Account;
import saphy.saphy.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccountResponse {
	private String bankName;
	private String accountNumber;

	public static MemberAccountResponse toDto(Member member) {
		MemberAccountResponse memberAccountResponse = new MemberAccountResponse();

		Account account = member.getAccount();
		memberAccountResponse.bankName = account.getBankName();
		memberAccountResponse.accountNumber = account.getAccountNumber();

		return memberAccountResponse;
	}
}
