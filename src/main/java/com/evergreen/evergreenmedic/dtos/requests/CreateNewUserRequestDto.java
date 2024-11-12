package com.evergreen.evergreenmedic.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateNewUserRequestDto {

    @NotBlank
    private String firstName;
    @NotBlank

    private String lastName;
    @NotBlank

    private String email;
    @NotBlank

    private String password;
    @NotBlank

    private String phoneNumber;
}
