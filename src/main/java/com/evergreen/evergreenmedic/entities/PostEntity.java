package com.evergreen.evergreenmedic.entities;


import com.evergreen.evergreenmedic.enums.MedicinePackage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "post_title", nullable = false, length = 255)
    private String postTitle;

    @Column(name = "post_description", nullable = false, length = 500)
    private String postDescription;

    @Column(name = "medicine_name", unique = false, nullable = false, length = 255)
    private String medicineName;

    @Column(name = "medicine_company", unique = false, nullable = false, length = 255)
    private String medicineCompany;

    @Column(name = "medicine_quantity", nullable = false, length = 255)
    private Short medicineQuantity;

    @Column(name = "medicine_packaging", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private MedicinePackage medicinePackage;

    @Column(name = "medicine_dosage", nullable = false, length = 255)
    private String medicineDosage;

    @Column(name = "notes", nullable = true)
    private String notes;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity user;


    @OneToMany(targetEntity = StaticResourceEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_files", referencedColumnName = "id")
    private List<StaticResourceEntity> files;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, updatable = true)
    @UpdateTimestamp
    private Instant updatedAt;


}
