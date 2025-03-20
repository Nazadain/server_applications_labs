package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.exception.factory.PolicyExceptionFactory;
import ru.nikita.labs.exception.factory.UserExceptionFactory;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.service.JwtDataService;

import java.util.Arrays;
import java.util.List;

@Component
public class UserPermissionInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final JwtDataService jwtDataService;

    public UserPermissionInterceptor(UserRepository userRepository,
                                     JwtDataService jwtDataService) {
        this.userRepository = userRepository;
        this.jwtDataService = jwtDataService;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) {
        String path = req.getRequestURI();
        String[] paths = path.split("/");

        if (paths.length > 4) {
            Long id = jwtDataService.getUserId(req);
            User user = userRepository.findById(id)
                    .orElseThrow(UserExceptionFactory::notFound);
            List<RoleDto> roles = user.roles();
            for (RoleDto role : roles) {
                if (role.getCode().equals("a100")) {
                    return true;
                }
            }
            String username = jwtDataService.getUsername(req);
            if (!user.getUsername().equals(username)) {
                throw PolicyExceptionFactory.forbidden();
            }
        }

        return true;
    }
}
