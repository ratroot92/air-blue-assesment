package com.evergreen.evergreenmedic.dtos.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCompleteUserReqDto {
    @NotNull
    private Short id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
