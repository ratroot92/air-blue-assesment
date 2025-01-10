package com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement;

import com.evergreen.evergreenmedic.entities.kyc.KycVerification;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PerformKycReqDto {

    @NotNull
    private int KycRecordId;
    @NotNull
    private KycVerification kycVerification;


}
