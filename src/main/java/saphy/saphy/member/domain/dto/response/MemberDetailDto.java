package saphy.saphy.member.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import saphy.saphy.member.domain.Member;

@Getter
@Builder
public class MemberDetailDto {

    private String loginId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;

    public static MemberDetailDto toDto(Member member) {
        return MemberDetailDto.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }
}
