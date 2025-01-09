package com.evergreen.evergreenmedic.controllers.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycLevel;
import com.evergreen.evergreenmedic.services.kyc.KycLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/kyc-level")
public class KycLevelController {

    private final KycLevelService kycLevelService;

    public KycLevelController(KycLevelService kycLevelService) {
        this.kycLevelService = kycLevelService;
    }

    @GetMapping("")
    public ResponseEntity<List<KycLevel>> getAllKycLevels() {
        return ResponseEntity.status(HttpStatus.OK).body(kycLevelService.getAllKycLevels());
    }
}
