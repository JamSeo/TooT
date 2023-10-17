package com.realfinal.toot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정을 활성화시켜주는 어노테이션
@Configuration
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.anyRequest().permitAll()
                );

        return http.build();
    }



//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests.anyRequest().permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        // 비밀번호를 DB에 저장하기 전 사용할 암호화
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
//        return (web)
//                -> web
//                .ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
//        http
//                // jwt 토큰 사용을 위한 설정
//                .csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                // 예외 처리
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //customEntryPoint
//                .accessDeniedHandler(jwtAccessDeniedHandler) // cutomAccessDeniedHandler
//
//                .and()
//                .authorizeRequests() // '인증'이 필요하다
//                .requestMatchers("/api/lunch").permitAll()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/api/best").permitAll()
//                .requestMatchers("/api/verify/**").permitAll()
//                .requestMatchers("/api/ads/active").permitAll()
//                .requestMatchers("/api/boards").permitAll()
//                .requestMatchers("/api/articles/board/**").permitAll()
//                .requestMatchers("/api/swagger-ui/**").permitAll()
//                .requestMatchers("/api/swagger-ui").permitAll()
//                .requestMatchers("/v3/api-docs/swagger-config").permitAll()
//                .requestMatchers("/v3/api-docs").permitAll()
//                .requestMatchers("/api/users").hasRole("ADMIN")
//                .anyRequest().authenticated()
//
//                .and()
//                .headers()
//                .frameOptions().sameOrigin()
//                .xssProtection()
//                .and()
//                .contentSecurityPolicy("script-src 'self'");
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }


}