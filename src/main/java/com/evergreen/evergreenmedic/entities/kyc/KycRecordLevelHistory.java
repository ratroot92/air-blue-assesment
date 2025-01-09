package com.evergreen.evergreenmedic.entities.kyc;


import com.evergreen.evergreenmedic.enums.kyc.KycRecordLevelHistoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity(name = "kyc_record_level_history")
@Table(name = "kyc_record_level_history")
@Data
public class KycRecordLevelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "kyc_record_id")
    @JsonIgnoreProperties("kycHistory")
    private KycRecord kycRecord;


    @ManyToOne()
    @JoinColumn(name = "kyc_level_id")
    @JsonIgnoreProperties
    private KycLevel kycLevel;


    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private KycRecordLevelHistoryStatus status = KycRecordLevelHistoryStatus.PENDING;

    @Column(name = "comments", nullable = false)
    private String comments;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt = Instant.now();
}
