package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRequirement;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KycRequirementRepository extends JpaRepository<KycRequirement, Integer> {
    List<KycRequirement> findAllByKycLevelLevelName(KycLevelName kycLevelName);
}
