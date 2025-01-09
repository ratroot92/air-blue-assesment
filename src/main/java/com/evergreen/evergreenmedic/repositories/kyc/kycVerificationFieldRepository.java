package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycVerificationField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface kycVerificationFieldRepository extends JpaRepository<KycVerificationField, Integer> {
}
