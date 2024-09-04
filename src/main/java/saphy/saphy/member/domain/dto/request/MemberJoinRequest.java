package saphy.saphy.member.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import saphy.saphy.member.domain.SocialType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequest {

    @NotNull(message = "아이디를 입력해주세요!")
    private String loginId;

    private String password;

    private SocialType socialType;

    private String name;

    private String nickName;

    private String address;

    private String phoneNumber;

    private String email;

}
