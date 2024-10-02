package saphy.saphy.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

@Getter
@Builder
public class MemberDetailResponse {
    private String loginId;
    private SocialType socialType;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;

    public static MemberDetailResponse toDto(Member member) {
        return MemberDetailResponse.builder()
                .loginId(member.getLoginId())
                .socialType(member.getSocialType())
                .name(member.getName())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }
}
