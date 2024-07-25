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

    @NotNull(message = "비밀번호를 입력해주세요!")
    private String password;

    private SocialType socialType;

    private String name;

    private String nickName;

    private String address;

    private String phoneNumber;

    private String email;

    @Builder.Default
    private Boolean isAdmin = Boolean.FALSE;

    public static Member toEntity(JoinMemberDto joinMemberDto) {
        return Member.builder()
                .loginId(joinMemberDto.getLoginId())
                .password(joinMemberDto.getPassword())
                .socialType(joinMemberDto.getSocialType())
                .name(joinMemberDto.getName())
                .nickName(joinMemberDto.getNickName())
                .address(joinMemberDto.getAddress())
                .phoneNumber(joinMemberDto.getPhoneNumber())
                .email(joinMemberDto.getEmail())
                .isAdmin(joinMemberDto.getIsAdmin())
                .build();
    }

}
