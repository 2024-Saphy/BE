package saphy.saphy.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") // test용
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                // .allowCredentials(true) CORS 정책상 all 허용 불가
                .allowedOriginPatterns("*")
                .exposedHeaders("Authorization");
    }
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("https://saphy.site/","http://localhost:8080","http://localhost:3000")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("Authorization", "Content-Type")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true);
//    }
}
