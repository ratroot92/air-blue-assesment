package com.evergreen.evergreenmedic.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginByEmailReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
