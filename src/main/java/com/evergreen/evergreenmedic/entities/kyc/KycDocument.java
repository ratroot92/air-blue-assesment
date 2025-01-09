package com.evergreen.evergreenmedic.entities.kyc;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "kyc_document")
@Entity(name = "kyc_document")
@Data
public class KycDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "url", nullable = false)
    private String url;

}
