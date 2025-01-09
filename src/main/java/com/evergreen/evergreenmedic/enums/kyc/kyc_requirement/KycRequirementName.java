package com.evergreen.evergreenmedic.enums.kyc.kyc_requirement;

public enum KycRequirementName {
    FULL_NAME_VERIFICATION("First Name & Last Name Verification"),
    EMAIL_VERIFICATION("Email Verification"),
    PHONE_NUMBER_VERIFICATION("Phone Number Verification"),
    COUNTRY_RESIDENCE_VERIFICATION("Country of Residence Verification"),
    PASSPORT_VERIFICATION("Passport Verification"),
    CNIC_VERIFICATION("CNIC Verification"),
    DOB_VERIFICATION("Date of Birth Verification"),
    LIVELINESS_VERIFICATION("Liveliness Verification");

    private final String description;

    KycRequirementName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
