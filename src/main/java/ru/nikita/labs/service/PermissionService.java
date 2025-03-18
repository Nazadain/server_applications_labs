package ru.nikita.labs.service;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.dto.PermissionDto;
import ru.nikita.labs.dto.mapper.PermissionMapper;
import ru.nikita.labs.dto.request.PermissionRequest;
import ru.nikita.labs.exception.PolicyException;
import ru.nikita.labs.exception.factory.PolicyExceptionFactory;
import ru.nikita.labs.model.Permission;
import ru.nikita.labs.repository.PermissionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.nikita.labs.exception.factory.PolicyExceptionFactory.codeAlreadyExists;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final EntityManager entityManager;

    @Transactional
    public List<PermissionDto> findAll() {
        enableActiveRoleFilter();
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionDtos.add(PermissionMapper.toDto(permission));
        }
        return permissionDtos;
    }

    @Transactional
    public PermissionDto findByCode(String code) {
        enableActiveRoleFilter();
        Permission permission = permissionRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Permission not found"));
        return PermissionMapper.toDto(permission);
    }

    @Transactional
    public PermissionDto save(PermissionRequest permissionRequest) throws PolicyException {
        if (permissionRepository.findByCode(permissionRequest.getCode()).isPresent()) {
            throw codeAlreadyExists();
        }
        Permission permission = PermissionMapper.toEntity(permissionRequest);
        System.out.println(permission);
        permissionRepository.save(permission);
        return PermissionMapper.toDto(permission);
    }

    @Transactional
    public PermissionDto update(String code,
                                PermissionRequest permissionRequest) {
        enableActiveRoleFilter();
        Permission permission = permissionRepository.findByCode(code)
                .orElseThrow(PolicyExceptionFactory::permissionNotFound);
        permission.setName(permissionRequest.getName());
        permission.setCode(permissionRequest.getCode());
        permission.setDescription(permissionRequest.getDescription());
        return PermissionMapper.toDto(permissionRepository.save(permission));
    }

    @Transactional
    public void delete(String code) {
        permissionRepository.deleteByCode(code);
    }

    @Transactional
    public void softDelete(String code) {
        enableActiveRoleFilter();
        Permission permission = permissionRepository
                .findByCode(code)
                .orElseThrow(PolicyExceptionFactory::permissionNotFound);
        permission.setDeletedAt(LocalDateTime.now());
        permissionRepository.save(permission);
    }

    @Transactional
    public void restore(String code) {
        Permission permission = permissionRepository.findByCode(code)
                .orElseThrow(PolicyExceptionFactory::permissionNotFound);
        permission.setDeletedAt(null);
        permissionRepository.save(permission);
    }

    private void enableActiveRoleFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activePermissionFilter");
    }

    @Autowired
    public PermissionService(PermissionRepository permissionRepository,
                             EntityManager entityManager) {
        this.permissionRepository = permissionRepository;
        this.entityManager = entityManager;
    }
}
