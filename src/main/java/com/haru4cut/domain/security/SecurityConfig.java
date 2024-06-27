package com.haru4cut.domain.security;

import com.haru4cut.domain.jwt.JwtAuthenticationFilter;
import com.haru4cut.domain.jwt.JwtTokenProvider;
import com.haru4cut.domain.oauth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(new AntPathRequestMatcher("/error"))
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher("/"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)          // csrf 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))          // cors 설정
                .httpBasic(AbstractHttpConfigurer::disable)     // 기본 로그인 사용 안함
                .formLogin(AbstractHttpConfigurer::disable)     // 폼 비활성화
                .logout(AbstractHttpConfigurer::disable)        // 기본 로그아웃 사용 안함
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())     // X-Frame-Options 비활성화
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))        // 세션 사용 안함

                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                    new AntPathRequestMatcher("/users/login/**")        // 로그인 요청 허용하기
                                )
                                .permitAll()
                                .requestMatchers( HttpMethod.POST,"/image/**", "diaries/{id:\\d+}/events")
                                .hasAuthority("ROLE_SUBSCRIBER")
                                .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );

        return http.build();
    }

    // CORS 관련 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("https://haru4cut.github.io")); // frontend url
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
