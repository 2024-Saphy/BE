package saphy.saphy.member.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoUpdateDto {

    private String password;

    private String name;

    private String nickName;

    private String address;

    private String phoneNumber;

    private String email;
}
