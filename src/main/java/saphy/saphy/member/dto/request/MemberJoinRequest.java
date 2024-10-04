package saphy.saphy.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import saphy.saphy.member.domain.Member;
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

    private String phoneNumber;

    private String email;

    public Member toEntity(String encodedPassword){
        return Member.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .socialType(socialType)
                .name(name)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .email(email)
                .isAdmin(Boolean.FALSE) // 기본값 설정
                .build();
    }
}
