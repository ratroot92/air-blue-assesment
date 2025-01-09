package com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PerformKycReqDto {

    @NotNull
    private int KycRecordId;
    @NotNull
    private String kycVerification;
//    @NotNull
//    private MultipartFile file;


}
