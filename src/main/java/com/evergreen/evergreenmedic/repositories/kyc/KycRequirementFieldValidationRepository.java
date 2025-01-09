package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRequirementFieldValidationRepository extends JpaRepository<KycRequirementFieldValidation, Integer> {
}
