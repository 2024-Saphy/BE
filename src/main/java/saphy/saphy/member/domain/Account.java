package saphy.saphy.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Account {
	private String bankName;
	private String accountNumber;

	public static Account of(String bankName, String accountNumber) {
		Account newAccount = new Account();

		newAccount.bankName = bankName;
		newAccount.accountNumber = accountNumber;

		return newAccount;
	}
}
