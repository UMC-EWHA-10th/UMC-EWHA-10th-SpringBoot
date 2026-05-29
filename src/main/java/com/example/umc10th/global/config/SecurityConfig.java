package com.example.umc10th.global.config;

import com.example.umc10th.global.security.filter.JwtAuthFilter;
import com.example.umc10th.global.security.handler.CustomAccessDenied;
import com.example.umc10th.global.security.handler.CustomEntryPoint;
import com.example.umc10th.global.security.handler.OAuthSuccessHandler;
import com.example.umc10th.global.security.service.CustomOAuthService;
import com.example.umc10th.global.security.service.CustomUserDetailsService;
import com.example.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuthService customOAuthService;

    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",

            // 로그인
            "/auth/**",

            // OAuth
            "/oauth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // URI 허용 여부
                .authorizeHttpRequests(requests -> requests
                        // Public API 허용
                        .requestMatchers(allowUris).permitAll()
                        // 그 이외 API는 인증 필요
                        .anyRequest().authenticated()
                )
                // 폼 로그인
                .formLogin(AbstractHttpConfigurer::disable)
                // 세션: JWT는 Stateless지만 OAuth2 state 검증을 위해 IF_REQUIRED 사용
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // JWT 필터
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // OAuth
                .oauth2Login(oauth -> oauth
                        // 인증 엔트리 포인트
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth/authorize")
                        )
                        // 콜백 주소
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/oauth/callback/**")
                        )
                        // 인증 완료 후 정보 활용
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuthService)
                        )
                        // 성공 시 JWT 토큰 발행할 핸들러
                        .successHandler(oAuthSuccessHandler())
                        // 실패 시 JSON으로 에러 반환 (디버그용)
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            new ObjectMapper().writeValue(
                                    response.getOutputStream(),
                                    Map.of(
                                            "isSuccess", false,
                                            "code", "OAUTH_ERROR",
                                            "message", exception.getMessage() != null ? exception.getMessage() : "OAuth2 인증 실패",
                                            "cause", exception.getClass().getSimpleName()
                                    )
                            );
                        })
                )
                // 예외 상황 핸들러
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied())
                        .authenticationEntryPoint(customEntryPoint())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  CustomAccessDenied customAccessDenied() {
        return new CustomAccessDenied();
    }

    @Bean
    public  CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(jwtUtil);
    }
}