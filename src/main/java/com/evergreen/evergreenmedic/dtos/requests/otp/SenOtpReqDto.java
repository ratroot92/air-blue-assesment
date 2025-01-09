package com.evergreen.evergreenmedic.dtos.requests.otp;


import com.evergreen.evergreenmedic.enums.OtpValidFor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SenOtpReqDto {
    @NotNull
    private String email;

    @NotNull
    private OtpValidFor otpValidFor;
}
