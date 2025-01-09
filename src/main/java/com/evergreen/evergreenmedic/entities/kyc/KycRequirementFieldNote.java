package com.evergreen.evergreenmedic.entities.kyc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "kyc_requirement_field_notes")
@Table(name = "kyc_requirement_field_notes")
@Data
public class KycRequirementFieldNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kyc_requirement_field_id", nullable = false)
    @JsonIgnoreProperties("notes")
    private KycRequirementField kycRequirementField;

    @Column(name = "note", nullable = false)
    private String note;

}
