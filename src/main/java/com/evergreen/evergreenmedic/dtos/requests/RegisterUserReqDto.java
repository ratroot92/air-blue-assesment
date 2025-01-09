package com.evergreen.evergreenmedic.dtos.requests;

import com.evergreen.evergreenmedic.enums.user.UserGender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserReqDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String phoneNumber;
    @NotNull
    private UserGender gender;
    @NotNull
    private Integer otp;

    public boolean isConfirmPasswordValid() {
        return confirmPassword != null && !confirmPassword.isEmpty() && confirmPassword.equals(password);
    }
}


