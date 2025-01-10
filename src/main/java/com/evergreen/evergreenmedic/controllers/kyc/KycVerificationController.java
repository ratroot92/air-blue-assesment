package com.evergreen.evergreenmedic.controllers.kyc;


import com.evergreen.evergreenmedic.entities.kyc.KycVerification;
import com.evergreen.evergreenmedic.services.kyc.KycVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/kyc-verifications")
public class KycVerificationController {


    private final KycVerificationService kycVerificationService;

    public KycVerificationController(KycVerificationService kycVerificationService) {
        this.kycVerificationService = kycVerificationService;
    }

    @GetMapping()
    public ResponseEntity<List<KycVerification>> getAllKycVerifications() {
        return ResponseEntity.status(HttpStatus.OK).body(kycVerificationService.fetchAllKycVerifications());
    }
}
