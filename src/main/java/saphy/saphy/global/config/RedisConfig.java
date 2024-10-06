package saphy.saphy.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.jwt.redis.host}")
    private String host;

    @Value("${spring.jwt.redis.port}")
    private int port;

    @Value("${spring.jwt.redis.password}")
    private String password;

    // Redis 서버 연결 설정을 관리하는 객체
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 객체 생성 후 Redis 서버의 호스트, 포트, 비밀번호를 설정
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        // 비밀번호가 설정되어 있으면 이를 적용합니다. 비밀번호가 없을 경우 이 코드는 비밀번호 설정을 건너뜁니다.
        if (password != null && !password.isEmpty()) {
            config.setPassword(password);
        }
        // 설정을 통해 Redis 연결 생성
        return new JedisConnectionFactory(config);
    }

    @Bean // RedisTemplate 빈 생성: Redis 서버와 상호작용하기 위한 템플릿 객체
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());  // Redis 연결 팩토리 설정

        // 일반적인 key:value 구조의 데이터에 대한 시리얼라이저 설정 (key와 value를 문자열로 변환)
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // Hash 타입 데이터를 처리할 때 사용할 시리얼라이저 설정
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        // 기본 시리얼라이저 설정 (모든 경우에 적용)
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        return redisTemplate;  // 설정된 RedisTemplate 반환
    }
}
