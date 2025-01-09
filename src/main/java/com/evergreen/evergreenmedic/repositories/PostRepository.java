package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.PostEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    List<PostEntity> findPostEntitiesByUser(UserEntity userEntity);
}
