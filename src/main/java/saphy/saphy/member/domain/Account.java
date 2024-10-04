package saphy.saphy.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Account {
	private String bankName;
	private String accountNumber;
}
