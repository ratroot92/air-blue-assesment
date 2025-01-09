package com.evergreen.evergreenmedic.dtos.response;

import lombok.Data;

@Data
public class SendOtpResponseDto {
    private boolean isOtpSent;
    private String email;
}
