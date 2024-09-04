package saphy.saphy.auth.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import saphy.saphy.member.domain.SocialType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginRequest {

    @NotNull(message = "소셜 타입은 필수 항목입니다.")
    private SocialType socialType;  // 소셜 로그인 제공자 (예: KAKAO, GOOGLE, NAVER 등)

    @NotBlank(message = "소셜 아이디는 필수 항목입니다.")
    private String email;  // 소셜 로그인에서 사용하는 고유 사용자 ID
}
