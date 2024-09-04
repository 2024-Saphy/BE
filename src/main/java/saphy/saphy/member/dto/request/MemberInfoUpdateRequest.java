package saphy.saphy.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoUpdateRequest {
    private String password;

    private String name;

    private String nickName;

    private String address;

    private String phoneNumber;

    private String email;
}
