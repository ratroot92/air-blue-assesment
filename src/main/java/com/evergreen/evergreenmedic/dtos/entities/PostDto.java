package com.evergreen.evergreenmedic.dtos.entities;

import com.evergreen.evergreenmedic.entities.PostEntity;
import com.evergreen.evergreenmedic.entities.StaticResourceEntity;
import com.evergreen.evergreenmedic.enums.MedicinePackage;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data

public class PostDto {
    private Integer id;
    private String postTitle;
    private String postDescription;
    private String medicineName;
    private Short medicineQuantity;
    private String notes;
    private String contactNumber;
    private Integer userId;
    private MedicinePackage medicinePackage;
    private String medicineDosage;
    private List<StaticResourceEntity> files;
    private Instant createdAt;
    private Instant updatedAt;

    public static PostDto entityToDto(PostEntity postEntity) {
        System.out.println("==========================");
        System.out.println("entityToDto => " + postEntity);
        System.out.println("==========================");
        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setPostTitle(postEntity.getPostTitle());
        postDto.setPostDescription(postEntity.getPostDescription());
        postDto.setMedicineName(postEntity.getMedicineName());
        postDto.setMedicineQuantity(postEntity.getMedicineQuantity());
        postDto.setNotes(postEntity.getNotes());
        postDto.setContactNumber(postEntity.getContactNumber());
        postDto.setUserId(postEntity.getUser().getId());
        postDto.setFiles(postEntity.getFiles());
        postDto.setMedicinePackage(postEntity.getMedicinePackage());
        postDto.setMedicineDosage(postEntity.getMedicineDosage());
        postDto.setCreatedAt(postEntity.getCreatedAt());
        postDto.setUpdatedAt(postEntity.getUpdatedAt());
        System.out.println("==========================");
        System.out.println("postEntity.getCreatedAt() => " + postEntity.getCreatedAt());
        System.out.println("postEntity.getUpdatedAt() => " + postEntity.getUpdatedAt());
        System.out.println("==========================");
        return postDto;


    }


}
