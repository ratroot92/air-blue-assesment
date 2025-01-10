package com.evergreen.evergreenmedic.enums.kyc.kyc_requirement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum KycRequirementActionUri {
    FIRSTNAME_LASTNAME_VERIFICATION_URI("/kyc-record/perform"),
    SEND_EMAIL_OTP("/otp/email"),
    SEND_PHONE_OTP("/kyc-record/perform");

    private final String uri;
}
