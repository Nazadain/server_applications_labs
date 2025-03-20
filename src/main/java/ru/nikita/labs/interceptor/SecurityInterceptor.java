package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.exception.PolicyException;
import ru.nikita.labs.exception.factory.UserExceptionFactory;
import ru.nikita.labs.model.User;
import ru.nikita.labs.security.Permission;
import ru.nikita.labs.security.RequiresPermission;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.service.AuthService;
import ru.nikita.labs.service.JwtDataService;

import java.util.List;

import static ru.nikita.labs.exception.factory.PolicyExceptionFactory.forbidden;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private final AuthService authService;
    private final JwtDataService jwtDataService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws PolicyException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresPermission requiresPermission =
                handlerMethod.getMethodAnnotation(RequiresPermission.class);
        if (requiresPermission == null) {
            return true;
        }
        Permission[] requiredPermissions = requiresPermission.value();

        String username = jwtDataService.getUsername(req);
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserExceptionFactory::notFound);
        List<RoleDto> roles = user.roles();

        for (Permission permission : requiredPermissions) {
            String code = permission.getCode();
            if (!authService.hasPermission(roles, code)) {
                throw forbidden(permission.getName());
            }
        }
        return true;
    }

    @Autowired
    public SecurityInterceptor(AuthService authService,
                               JwtDataService jwtDataService,
                               UserRepository userRepository) {
        this.authService = authService;
        this.jwtDataService = jwtDataService;
        this.userRepository = userRepository;
    }
}
