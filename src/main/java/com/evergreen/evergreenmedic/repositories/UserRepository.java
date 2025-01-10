package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

//    UserEntity save(UserEntity userEntity);


}
