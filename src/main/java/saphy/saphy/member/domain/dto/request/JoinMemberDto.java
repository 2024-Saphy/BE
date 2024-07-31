package saphy.saphy.member.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinMemberDto {

    @NotNull(message = "아이디를 입력해주세요!")
    private String loginId;

    private String password;

    private SocialType socialType;

    private String name;

    private String nickName;

    private String address;

    private String phoneNumber;

    private String email;

    @Builder.Default
    private Boolean isAdmin = Boolean.FALSE;

    public static Member toEntity(JoinMemberDto request) {
        return Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .socialType(request.getSocialType())
                .name(request.getName())
                .nickName(request.getNickName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .isAdmin(request.getIsAdmin())
                .build();
    }

}
