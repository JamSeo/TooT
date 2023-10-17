package com.realfinal.toot.config;

import com.realfinal.toot.common.util.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
            .addMapping("/chatbot/**")  // 허용할 URL 패턴 설정
            .allowedOrigins("http://localhost:3000", "https://too-t.com")  // 허용할 오리진(도메인) 설정
            .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")  // 허용할 HTTP 메소드 설정
            .allowedHeaders("*")  // 허용할 헤더 설정
            .allowCredentials(true);
        registry
            .addMapping("/rank/**")  // 허용할 URL 패턴 설정
            .allowedOrigins("http://localhost:3000", "https://too-t.com")  // 허용할 오리진(도메인) 설정
            .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")  // 허용할 HTTP 메소드 설정
            .allowedHeaders("*")  // 허용할 헤더 설정
            .allowCredentials(true);
        registry
            .addMapping("/quiz/**")  // 허용할 URL 패턴 설정
            .allowedOrigins("http://localhost:3000", "https://too-t.com")  // 허용할 오리진(도메인) 설정
            .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS")  // 허용할 HTTP 메소드 설정
            .allowedHeaders("*")  // 허용할 헤더 설정
            .allowCredentials(true);
        registry
            .addMapping("/stock/**")  // 허용할 URL 패턴 설정
            .allowedOrigins("http://localhost:3000", "https://too-t.com")  // 허용할 오리진(도메인) 설정
            .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS")  // 허용할 HTTP 메소드 설정
            .allowedHeaders("*")  // 허용할 헤더 설정
            .allowCredentials(true);
        registry
            .addMapping("/user/**")  // 허용할 URL 패턴 설정
            .allowedOrigins("http://localhost:3000", "https://too-t.com")  // 허용할 오리진(도메인) 설정
            .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")  // 허용할 HTTP 메소드 설정
            .allowedHeaders("*")  // 허용할 헤더 설정
            .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login/kakao",
                        "/user/refresh",
                        "/user/logout",
                        "/stock/rank",
                        "/rank/list",

                        // swagger, 문서관련
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs"
                );
    }
}
