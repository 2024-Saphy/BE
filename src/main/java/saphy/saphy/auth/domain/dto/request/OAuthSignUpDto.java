package saphy.saphy.auth.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.SocialType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthSignUpDto {

    @NotNull
    private String email;

    private String name;

    // 이거 플러터에서 줄 수 있는데, 엔티티에 없는 필드라 일단 주석 처리
    // private String profilePhotoUrl;

    private String phoneNumber;

    private SocialType socialType;

    public static Member toEntity(OAuthSignUpDto request){
        return Member.builder()
                .loginId(request.getEmail())
                .email(request.getEmail())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .socialType(request.getSocialType())
                .build();
    }
}
