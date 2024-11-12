package com.evergreen.evergreenmedic.dtos.requests.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserEmailReqDto {
    @NotNull
    String email;
}
