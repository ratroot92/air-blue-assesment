package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.StaticResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StaticResourceRepository extends JpaRepository<StaticResourceEntity, Short> {


}