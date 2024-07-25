package saphy.saphy.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                // 이 부분 서버 개설 + 플러터 포트번호 얘기 후 수정
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:8080"
                );
    }
}
