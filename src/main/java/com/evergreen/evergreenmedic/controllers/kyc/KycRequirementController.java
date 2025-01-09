package com.evergreen.evergreenmedic.controllers.kyc;


import com.evergreen.evergreenmedic.entities.kyc.KycRequirement;
import com.evergreen.evergreenmedic.services.kyc.KycRequirementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kyc-requirement")
public class KycRequirementController {
    private final KycRequirementService kycRequirementService;

    public KycRequirementController(KycRequirementService kycRequirementService) {
        this.kycRequirementService = kycRequirementService;
    }

    @GetMapping()
    public ResponseEntity<List<KycRequirement>> getKycRequirements() {
        return ResponseEntity.ok().body(kycRequirementService.getAllKycRequirements());
    }
}
