package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.enums.kyc.KycVerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Table(name = "kyc_verification")
@Entity(name = "kyc_verification")
@Data
public class KycVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "kyc_record_id", nullable = false)
    @JsonIgnoreProperties("kycVerifications")
    private KycRecord kycRecord;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "kyc_requirement_id", nullable = false)
    @JsonIgnoreProperties("kycLevel")
    private KycRequirement kycRequirement;

    @Column(name = "status", nullable = false)
    private KycVerificationStatus status = KycVerificationStatus.NOT_ATTEMPTED;

    @OneToMany(mappedBy = "KycVerification", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("KycVerification")
    private List<KycVerificationField> kycVerificationFields = new ArrayList<KycVerificationField>();

    @Column(name = "sequence", nullable = false)
    private int sequence;

    @Column(name = "locked", nullable = false)
    private boolean isLocked;


}

