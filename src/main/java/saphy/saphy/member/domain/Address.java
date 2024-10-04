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
public class Address {
	private String address;

	private String detailAddress;

	public static Address of(String address, String detailAddress) {
		Address newAddress = new Address();

		newAddress.address = address;
		newAddress.detailAddress = detailAddress;

		return newAddress;
	}
}
