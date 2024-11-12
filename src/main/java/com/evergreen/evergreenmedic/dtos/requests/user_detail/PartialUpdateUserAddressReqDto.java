package com.evergreen.evergreenmedic.dtos.requests.user_detail;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PartialUpdateUserAddressReqDto {

    @NotNull
    private UUID id;

    private String country;


    private String state;


    private String city;


    private String street;


    private String house;


    private Integer postalCode;

    private String descriptiveAddress;


    private String primaryPhoneNumber;

    private String secondaryPhoneNumber;

}
