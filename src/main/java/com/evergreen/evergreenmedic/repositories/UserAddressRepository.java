package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.UserAddressEntity;
import com.evergreen.evergreenmedic.entities.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, UUID> {

    List<UserAddressEntity> findAllByUserDetailEntity(UserDetailEntity userDetailEntity);
}
