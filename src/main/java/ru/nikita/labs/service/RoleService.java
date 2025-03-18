package ru.nikita.labs.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.mapper.RoleMapper;
import ru.nikita.labs.dto.request.RoleRequest;
import ru.nikita.labs.exception.PolicyException;
import ru.nikita.labs.exception.factory.PolicyExceptionFactory;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.nikita.labs.exception.factory.PolicyExceptionFactory.codeAlreadyExists;

@Service
public class RoleService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;

    public List<RoleDto> findAll() {
        enableActiveRoleFilter();
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(RoleMapper.toDto(role));
        }
        return roleDtos;
    }

    @Transactional
    public RoleDto findByCode(String code) throws PolicyException {
        enableActiveRoleFilter();
        Role role = roleRepository
                .findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        return RoleMapper.toDto(role);
    }

    @Transactional
    public RoleDto save(RoleRequest roleRequest) throws PolicyException {
        if (roleRepository.findByCode(roleRequest.getCode()).isPresent()) {
            throw codeAlreadyExists();
        }
        Role role = RoleMapper.toEntity(roleRequest);
        roleRepository.save(role);
        return RoleMapper.toDto(role);
    }

    @Transactional
    public RoleDto update(RoleRequest roleRequest,
                          String code) throws PolicyException {
        enableActiveRoleFilter();
        Role updatedRole = roleRepository.findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        updatedRole.setName(roleRequest.getName());
        updatedRole.setDescription(roleRequest.getDescription());
        updatedRole.setCode(roleRequest.getCode());
        roleRepository.save(updatedRole);
        return RoleMapper.toDto(updatedRole);
    }

    @Transactional
    public void delete(String code) {
        roleRepository.deleteByCode(code);
    }

    @Transactional
    public void softDelete(String code) throws PolicyException {
        Role role = roleRepository
                .findByCode(code)
                .orElseThrow(PolicyExceptionFactory::codeAlreadyExists);
        role.setDeletedAt(LocalDateTime.now());
        roleRepository.save(role);
    }

    @Transactional
    public void restore(String code) {
        Role role = roleRepository
                .findByCode(code)
                .orElseThrow(PolicyExceptionFactory::roleNotFound);
        role.setDeletedAt(null);
        roleRepository.save(role);
    }

    private void enableActiveRoleFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeRoleFilter");
    }

    @Autowired
    public RoleService(RoleRepository roleRepository,
                       EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }
}
