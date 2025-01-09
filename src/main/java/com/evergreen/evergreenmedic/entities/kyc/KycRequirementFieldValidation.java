package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementFieldValidationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "kyc_requirement_field_validations")
@Table(name = "kyc_requirement_field_validations")
@Data
public class KycRequirementFieldValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRequirementFieldValidationType type;

    @Column(name = "value")
    private String value;


    @ManyToOne
    @JoinColumn(name = "kyc_requirement_field_id")
    @JsonIgnoreProperties("kycRequirementFieldValidations")
    private KycRequirementField kycRequirementField;


}
