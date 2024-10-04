package saphy.saphy.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	private String address;
	private String detailAddress;
}
