package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycVerificationRepository extends JpaRepository<KycVerification, Integer> {
}
