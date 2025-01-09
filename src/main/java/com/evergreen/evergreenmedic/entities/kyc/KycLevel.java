package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.entities.RoleEntity;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Table(name = "kyc_level")
@Entity(name = "kyc_level")
@Data
public class KycLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "level_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private KycLevelName levelName;


    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "kycLevel", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("kycLevel")
    private List<RoleEntity> requiredForUserRoleEntities = new ArrayList<>();

    @Column(name = "status", nullable = false)
    private KycLevelStatus status = KycLevelStatus.ACTIVE;

    @OneToMany(mappedBy = "kycLevel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("kycLevel")
    @OrderBy("sequence ASC")
    private List<KycRequirement> kycRequirements = new ArrayList<>();


}
