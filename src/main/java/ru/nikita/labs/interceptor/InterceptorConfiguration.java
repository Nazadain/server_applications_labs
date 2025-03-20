package ru.nikita.labs.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;
    private final SecurityInterceptor securityInterceptor;
    private final UserPermissionInterceptor userPermissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/auth/login", "/error");
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(userPermissionInterceptor)
                .addPathPatterns("/api/ref/user/**", "/api/auth/**");
    }

    @Autowired
    public InterceptorConfiguration(JwtInterceptor jwtInterceptor,
                                    SecurityInterceptor securityInterceptor,
                                    UserPermissionInterceptor userPermissionInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.securityInterceptor = securityInterceptor;
        this.userPermissionInterceptor = userPermissionInterceptor;
    }
}
