package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.RoleEntity;
import com.evergreen.evergreenmedic.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(UserRole userRole);
}
