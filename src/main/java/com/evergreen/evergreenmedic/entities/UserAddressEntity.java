package com.evergreen.evergreenmedic.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_address")
public class UserAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    @JsonBackReference
    private UserDetailEntity userDetail;

    @Column(name = "country", nullable = true, updatable = true)
    private String country;

    @Column(name = "state", nullable = true, updatable = true)
    private String state;

    @Column(name = "city", nullable = true, updatable = true)
    private String city;

    @Column(name = "street", nullable = true, updatable = true)
    private String street;

    @Column(name = "house", nullable = true, updatable = true)
    private String house;

    @Column(name = "postal_code", nullable = true, updatable = true)
    private Integer postalCode;

    @Column(name = "descriptive_address", nullable = true, updatable = true)
    private String descriptiveAddress;

    @Column(name = "primary_phoneNumber", nullable = true, updatable = true)
    private String primaryPhoneNumber;

    @Column(name = "secondary_phoneNumber", nullable = true, updatable = true)
    private String secondaryPhoneNumber;


}
