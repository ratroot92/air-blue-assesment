package com.evergreen.evergreenmedic.services;

import com.evergreen.evergreenmedic.entities.RoleEntity;
import com.evergreen.evergreenmedic.enums.UserRole;
import com.evergreen.evergreenmedic.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<RoleEntity> seed() {
//        create admin role
        RoleEntity adminRoleEntity = new RoleEntity();
        adminRoleEntity.setName(UserRole.ADMIN);
        roleRepository.save(adminRoleEntity);
//        create agent role
        RoleEntity agentRoleEntity = new RoleEntity();
        agentRoleEntity.setName(UserRole.AGENT);
        roleRepository.save(agentRoleEntity);

//        create customer role
        RoleEntity customerRoleEntity = new RoleEntity();
        customerRoleEntity.setName(UserRole.CUSTOMER);
        roleRepository.save(customerRoleEntity);
        return List.of(adminRoleEntity, customerRoleEntity, agentRoleEntity);
    }
}
