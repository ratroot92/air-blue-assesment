package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementName;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementVerificationMethod;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementVerificationProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "kyc_requirement")
@Table(name = "kyc_requirement",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "verification_method", "verification_provider", "sequence"}))
public class KycRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "kyc_level_id", nullable = false)
    @JsonIgnoreProperties("kycRequirements")
    private KycLevel kycLevel;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRequirementName name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "verification_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRequirementVerificationMethod verificationMethod;

    @Column(name = "verification_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRequirementVerificationProvider verificationProvider;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @OneToMany(mappedBy = "kycRequirement", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("kycRequirement")
    private List<KycRequirementField> kycRequirementFields;


}
