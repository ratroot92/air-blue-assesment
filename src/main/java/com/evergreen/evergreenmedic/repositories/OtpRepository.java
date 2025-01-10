package com.evergreen.evergreenmedic.repositories;

import com.evergreen.evergreenmedic.entities.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Integer> {
    @Query(value = "SELECT * FROM otp WHERE otp_email = :email ORDER BY created_at ASC LIMIT 1", nativeQuery = true)
    Optional<OtpEntity> findByOtpEmail(@Param("email") String email);

}
