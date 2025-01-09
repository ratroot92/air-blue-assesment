package com.evergreen.evergreenmedic.dtos.requests.post;

import com.evergreen.evergreenmedic.enums.MedicinePackage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostReqDto {


    @NotBlank
    @Size(min = 1, max = 255)
    private String postTitle;

    @NotBlank
    @Size(min = 1, max = 500)
    private String postDescription;

    @NotBlank
    @Size(min = 1, max = 255)
    private String medicineName;

    @NotBlank
    @Size(min = 1, max = 255)
    private String medicineCompany;

    @NotBlank
    private Short medicineQuantity;

    @NotBlank
    private String medicineDosage;

    @NotBlank
    private MedicinePackage medicinePackage;


    @NotBlank
    @Size(min = 1, max = 500)
    private String notes;

    @NotBlank
    private String contactNumber;

}
