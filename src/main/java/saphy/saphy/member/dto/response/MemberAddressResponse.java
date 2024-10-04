package saphy.saphy.member.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.member.domain.Address;
import saphy.saphy.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressResponse {
	private String address;
	private String detailAddress;

	public static MemberAddressResponse toDto(Member member) {
		MemberAddressResponse memberAddressResponse = new MemberAddressResponse();

		Address address = member.getAddress();
		memberAddressResponse.address = address.getAddress();
		memberAddressResponse.detailAddress = address.getDetailAddress();

		return memberAddressResponse;
	}
}
