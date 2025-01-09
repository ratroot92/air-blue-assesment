package com.evergreen.evergreenmedic.entities;

import com.evergreen.evergreenmedic.entities.kyc.KycLevel;
import com.evergreen.evergreenmedic.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "roles")
@Table(name = "roles")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private UserRole name;

    @ManyToOne()
    @JoinColumn(name = "kyc_level_id")
    @JsonIgnoreProperties("requiredForUserRoleEntities")
    private KycLevel kycLevel;


}
