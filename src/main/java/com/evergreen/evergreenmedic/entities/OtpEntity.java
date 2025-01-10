package com.evergreen.evergreenmedic.entities;

import com.evergreen.evergreenmedic.enums.OtpType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity(name = "otp")
@Table(name = "otp")
@Data
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "otp_email")
    private String otpEmail;

    @Column(name = "code")
    private Integer code;

    @Enumerated(EnumType.STRING)
    private OtpType otpType;

    @Column(name = "expiration_time")
    private Integer expirationTime = 60;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false, updatable = true)
    @UpdateTimestamp
    private Instant updatedAt;
}
