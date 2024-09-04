package saphy.saphy.global.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // https:// 접근 가능하게 설정, 로컬 테스트는 아래 2줄 주석처리
        Server server = new Server();
        server.setUrl("https://saphy.site");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .name("JWT Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("access token을 넣어주세요!")))
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Saphy REST API Swagger Test Page")
                .description("made by Saphy Backend Team")
                .contact(new Contact()
                        .name("Saphy BE GitHub")
                        .url("https://github.com/2024-Saphy/BE"))
                .version("1.0.0");
    }
}