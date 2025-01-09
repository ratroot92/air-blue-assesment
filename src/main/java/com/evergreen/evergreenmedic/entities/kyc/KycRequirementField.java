package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementFieldType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "kyc_requirement_fields")
@Table(name = "kyc_requirement_fields")
@Data
public class KycRequirementField implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRequirementFieldType type;

    @Column(name = "label", nullable = false)
    private String label;


    @Column(name = "value", insertable = false, nullable = true)
    private String value;

    @Column(name = "unique_id", nullable = false, unique = true)
    private String uniqueId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kyc_requirement_id", nullable = false)
    @JsonIgnoreProperties("kycRequirementFields")
    private KycRequirement kycRequirement;


    @OneToMany(mappedBy = "kycRequirementField", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("kycRequirementField")
    private List<KycRequirementFieldValidation> kycRequirementFieldValidations;


    @OneToMany(mappedBy = "kycRequirementField", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("kycRequirementField")
    private List<KycRequirementFieldNote> notes;


    @PrePersist
    @PreUpdate
    private void generateUniqueId() {
        if (uniqueId == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            uniqueId = timestamp + "_" + kycRequirement.getId() + "_" + kycRequirement.getSequence() + "_" + kycRequirement.getName().toString().toLowerCase() + "_" + type.toString().toLowerCase() + "_" + label.replaceAll(" ", "_").toLowerCase();
        }
    }
}
