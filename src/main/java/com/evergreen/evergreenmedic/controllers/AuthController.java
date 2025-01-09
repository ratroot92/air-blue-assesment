package com.evergreen.evergreenmedic.controllers;


import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.requests.RegisterUserReqDto;
import com.evergreen.evergreenmedic.dtos.requests.UserLoginByEmailReqDto;
import com.evergreen.evergreenmedic.dtos.response.RegisterUserRespDto;
import com.evergreen.evergreenmedic.dtos.response.UserLoginReqByEmailRespDto;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserRespDto> registerUser(@RequestBody @Valid RegisterUserReqDto registerUserReqDto) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(authService.registerUser(registerUserReqDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginReqByEmailRespDto> loginByEmail(@RequestBody @Valid UserLoginByEmailReqDto userLoginByEmailReqDto, HttpServletResponse response) {

        return ResponseEntity.status(HttpStatus.OK).body(authService.loginByEmail(userLoginByEmailReqDto, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.logout(response));
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<ProtectedUserDto> isAuthenticated(@RequestAttribute("user") UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.isAuthenticated(userEntity));
    }
}
