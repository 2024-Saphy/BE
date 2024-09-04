package saphy.saphy.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthJoinRequest {
    @NotNull
    private String email;

    private String name;

    // 이거 플러터에서 줄 수 있는데, 엔티티에 없는 필드라 일단 주석 처리
    // private String profilePhotoUrl;

    private String phoneNumber;

    private SocialType socialType;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .loginId(email)
                .password(encodedPassword)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .socialType(socialType)
                .isAdmin(Boolean.FALSE)
                .build();
    }
}
