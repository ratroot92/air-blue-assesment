package com.evergreen.evergreenmedic.controllers.kyc;

import com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement.PerformKycReqDto;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.entities.kyc.KycRecord;
import com.evergreen.evergreenmedic.services.kyc.KycRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kyc-record")
public class KycRecordController {

    private final KycRecordService kycRecordService;

    public KycRecordController(KycRecordService kycRecordService) {
        this.kycRecordService = kycRecordService;
    }

    @PostMapping("/init")
    public ResponseEntity<KycRecord> init(@RequestAttribute("user") UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(kycRecordService.init(userEntity));

    }

    @PostMapping(value = "/perform")
    public ResponseEntity<KycRecord> performKyc(@ModelAttribute PerformKycReqDto performKycReqDto) throws BadRequestException, JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(kycRecordService.performKyc(performKycReqDto));
    }


}
