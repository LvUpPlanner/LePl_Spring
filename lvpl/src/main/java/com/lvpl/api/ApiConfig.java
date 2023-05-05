package com.lvpl.api;

import com.lvpl.api.interceptor.MemberCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정 파일임을 알림
@Slf4j
public class ApiConfig implements WebMvcConfigurer {
    
    // "/api/v1/members/login/123" 테스트용으로 삽입한것이라 제거해야함
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MemberCheckInterceptor())
                .order(2)
                .addPathPatterns("/**") // 모든 경로 접근
                .excludePathPatterns("/", "/api/v1/members/login", "/api/v1/members/register",
                        "/api/v1/members/logout","/css/**","/*.ico","/error","/api/v1/members/login/123"); // 제외 경로!

    }
}
