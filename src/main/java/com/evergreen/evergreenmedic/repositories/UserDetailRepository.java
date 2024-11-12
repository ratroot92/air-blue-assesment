package com.evergreen.evergreenmedic.repositories;


import com.evergreen.evergreenmedic.entities.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Short> {


}