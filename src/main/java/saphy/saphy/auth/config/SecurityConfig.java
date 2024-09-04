package saphy.saphy.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import saphy.saphy.auth.filter.CustomLogoutFilter;
import saphy.saphy.auth.filter.JWTFilter;
import saphy.saphy.auth.filter.LoginFilter;
import saphy.saphy.auth.repository.RefreshRepository;
import saphy.saphy.auth.utils.JWTUtil;
import saphy.saphy.member.domain.repository.MemberRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    private static final String[] PUBLIC_URLS = {
            "/health",
            "/oauth2/**",
            "/login/**",
            "/members/join",
            "/reissue",
            "/v3/**",
            "/swagger-ui/**",
            "/error",
            "/",
            "/**" // 개발 test용 api 모든 접근 허용 코드 추가
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/oauth2/login",
                "/oauth2/join"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CORS 설정
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();
//                                configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000", "https://saphy.site"));
                                configuration.setAllowedOrigins(Collections.singletonList("*"));
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);
                                configuration.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
                                return configuration;
                            }
                        }));

//        http
//                .cors((cors) -> cors
//                        .configurationSource((new CorsConfigurationSource() {
//
//                            @Override
//                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//
//                                CorsConfiguration configuration = new CorsConfiguration();
//
//                                configuration.setAllowedOrigins(Collections.singletonList("*"));
//                                configuration.setAllowedMethods(Collections.singletonList("*"));
//                                configuration.setAllowCredentials(true);
//                                configuration.setAllowedHeaders(Collections.singletonList("*"));
//                                configuration.setMaxAge(3600L);
//                                configuration.setExposedHeaders(Collections.singletonList("Authorization"));
//
//                                return configuration;
//                            }
//                        })));

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // 경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated());

        // JWT, 로그인, 로그아웃 커스텀 필터 삽입
        http
                .addFilterAfter(new JWTFilter(jwtUtil, memberRepository), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterAt(
                        new LoginFilter(authenticationManager(authenticationConfiguration), refreshRepository, jwtUtil, "/login"),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        http
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
