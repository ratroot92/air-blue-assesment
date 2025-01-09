package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycLevel;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KycLevelRepository extends JpaRepository<KycLevel, Integer> {

    Optional<KycLevel> findByLevelName(KycLevelName kycLevelName);
}
