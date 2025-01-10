package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycVerification;
import com.evergreen.evergreenmedic.repositories.kyc.KycVerificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycVerificationService {

    private final KycVerificationRepository kycVerificationRepository;

    public KycVerificationService(KycVerificationRepository kycVerificationRepository) {
        this.kycVerificationRepository = kycVerificationRepository;
    }

    public List<KycVerification> fetchAllKycVerifications() {
        return kycVerificationRepository.findAll();

    }
}
