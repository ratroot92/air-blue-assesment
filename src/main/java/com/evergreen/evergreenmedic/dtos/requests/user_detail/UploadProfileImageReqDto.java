package com.evergreen.evergreenmedic.dtos.requests.user_detail;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadProfileImageReqDto {

    @NotNull
    public String profileImage;
}
