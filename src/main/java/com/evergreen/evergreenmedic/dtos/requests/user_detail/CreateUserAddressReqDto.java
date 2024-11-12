package com.evergreen.evergreenmedic.dtos.requests.user_detail;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserAddressReqDto {


    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String house;

    @NotNull
    private Integer postalCode;

    private String descriptiveAddress;

    @NotNull
    private String primaryPhoneNumber;

    private String secondaryPhoneNumber;

}
