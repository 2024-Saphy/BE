package saphy.saphy.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "RefreshToken", timeToLive = 604800) // TTL 7일 설정
@AllArgsConstructor
public class RefreshEntity {
    @Id // JPA 의존성이 필요하지 않기 때문에 persistence.id를 import하면 오류남
    private String loginId;
    private String refreshToken;
}