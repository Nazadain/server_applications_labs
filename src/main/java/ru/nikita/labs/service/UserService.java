package ru.nikita.labs.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.mapper.RoleMapper;
import ru.nikita.labs.dto.mapper.UserMapper;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.exception.factory.PolicyExceptionFactory;
import ru.nikita.labs.exception.factory.UserExceptionFactory;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.model.User;
import ru.nikita.labs.model.UserRole;
import ru.nikita.labs.repository.RoleRepository;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.repository.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.nikita.labs.exception.factory.AuthExceptionFactory.emailAlreadyExists;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.userAlreadyExists;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    private final JwtDataService jwtDataService;

    @Transactional
    public List<UserDto> findAll() {
        enableActiveUserRoleFilter();
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toDto(user));
        }
        return userDtos;
    }

    @Transactional
    public List<RoleDto> getRoles(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserExceptionFactory::notFound);
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        List<RoleDto> roleDtos = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleDtos.add(RoleMapper.toDto(userRole.getRole()));
        }
        return roleDtos;
    }

    @Transactional
    public void setRole(HttpServletRequest req,
                        Long id,
                        String code) {
        enableActiveUserRoleFilter();
        User user = userRepository
                .findById(id)
                .orElseThrow(UserExceptionFactory::notFound);
        Role role = roleRepository
                .findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        UserRole userRole = UserRole.builder()
                .role(role)
                .user(user)
                .createdAt(LocalDateTime.now())
                .createdBy(jwtDataService.getUserId(req))
                .build();
        userRoleRepository.save(userRole);
    }

    @Transactional
    public void deleteRole(Long id, String code) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserExceptionFactory::notFound);
        Role role = roleRepository.findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        userRoleRepository.deleteByUserAndRole(user, role);
    }

    @Transactional
    public void softDeleteRole(HttpServletRequest req,
                               Long id,
                               String code) {
        UserRole userRole = findUserRole(id, code);
        userRole.setDeletedAt(LocalDateTime.now());
        userRole.setDeletedBy(jwtDataService.getUserId(req));
    }

    @Transactional
    public void restoreRole(Long id, String code) {
        UserRole userRole = findUserRole(id, code);
        userRole.setDeletedAt(null);
        userRole.setDeletedBy(null);
    }

    private UserRole findUserRole(Long id, String code) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserExceptionFactory::notFound);
        Role role = roleRepository.findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        return userRoleRepository.findByUserAndRole(user, role)
                .orElseThrow(PolicyExceptionFactory::userRoleNotFound);
    }

    @Transactional
    public UserDto create(RegisterRequest regRequest) {
        checkUsername(regRequest.getUsername());
        checkEmail(regRequest.getEmail());
        User user = UserMapper.toEntity(regRequest);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    private void enableActiveUserRoleFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeUserRoleFilter");
    }

    private void checkUsername(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw userAlreadyExists();
        }
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw emailAlreadyExists();
        }
    }

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       RoleRepository roleRepository,
                       EntityManager entityManager,
                       JwtDataService jwtDataService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
        this.jwtDataService = jwtDataService;
    }

}
