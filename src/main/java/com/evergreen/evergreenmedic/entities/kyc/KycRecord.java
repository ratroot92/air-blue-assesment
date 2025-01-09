package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.enums.kyc.KycRecordStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "kyc_record")
@Table(name = "kyc_record")
@Data
public class KycRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "kyc_level_id", nullable = false)
    @ManyToOne()
    @JsonIgnoreProperties({"kycRequirements", "requiredForUserRoles"})
    private KycLevel currentKycLevel;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne()
    @JsonIgnoreProperties({"kycRecord", "userDetail"})
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "kycRecord", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"kycRecord", "kycLevel"})
    private List<KycRecordLevelHistory> kycHistory = new ArrayList<KycRecordLevelHistory>();

    @OneToMany(mappedBy = "kycRecord", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("kycRecord")
    private List<KycVerification> kycVerifications = new ArrayList<KycVerification>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRecordStatus status = KycRecordStatus.PENDING;


}
