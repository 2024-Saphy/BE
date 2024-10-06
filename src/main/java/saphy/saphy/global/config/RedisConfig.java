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
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        // 비밀번호가 설정되어 있으면 적용
        if (password != null && !password.isEmpty()) {
            redisConfig.setPassword(password);
        }

        // JedisConnectionFactory를 통한 Redis 연결 생성
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisConfig);

        // JedisConnectionFactory 연결 풀 설정
        jedisConnectionFactory.getPoolConfig().setMaxTotal(50);  // 최대 커넥션 수
        jedisConnectionFactory.getPoolConfig().setMaxIdle(30);   // 최대 유휴 커넥션 수
        jedisConnectionFactory.getPoolConfig().setMinIdle(10);   // 최소 유휴 커넥션 수

        return jedisConnectionFactory;
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
