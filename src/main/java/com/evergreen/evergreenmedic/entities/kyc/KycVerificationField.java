package com.evergreen.evergreenmedic.entities.kyc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "kyc_verification_fields")
@Table(name = "kyc_verification_fields")
@Data
public class KycVerificationField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "kyc_verification_id", nullable = false)
    @JsonIgnoreProperties({"kycRecord", "kycRequirement", "kycVerificationFields"})
    private KycVerification KycVerification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kyc_requirement_field_id", nullable = false)
    @JsonIgnoreProperties({"kycRequirement", "kycRequirementFieldValidations"})
    private KycRequirementField kycRequirementField;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "field_value", nullable = false)
    private String fieldValue;


}
