package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.RouteRateLimiterEntity;
import com.evergreen.evergreenmedic.enums.CustomHttpMethodEnum;
import com.evergreen.evergreenmedic.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RouteRateLimiterRepository extends JpaRepository<RouteRateLimiterEntity, Short> {

    Optional<RouteRateLimiterEntity> findByRouteUrlAndRouteMethodAndUserRole(String routeUrl, CustomHttpMethodEnum routeMethod, UserRole userRole);
}
