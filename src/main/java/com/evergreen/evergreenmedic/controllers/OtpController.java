package com.evergreen.evergreenmedic.controllers;


import com.evergreen.evergreenmedic.dtos.requests.otp.SenOtpReqDto;
import com.evergreen.evergreenmedic.dtos.response.SendOtpResponseDto;
import com.evergreen.evergreenmedic.services.OtpService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping
    public ResponseEntity<SendOtpResponseDto> sendOtp(@RequestBody @Valid SenOtpReqDto senOtpReqDto) throws BadRequestException {
        boolean isSent = otpService.sendOtp(senOtpReqDto);
        SendOtpResponseDto sendOtpResponseDto = new SendOtpResponseDto();
        sendOtpResponseDto.setOtpSent(isSent);
        sendOtpResponseDto.setEmail(senOtpReqDto.getEmail());
        if (isSent) {
            return ResponseEntity.status(HttpStatus.OK).body(sendOtpResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sendOtpResponseDto);

        }

    }


}
