package com.evergreen.evergreenmedic.services;

import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.UserDto;
import com.evergreen.evergreenmedic.dtos.requests.RegisterUserReqDto;
import com.evergreen.evergreenmedic.dtos.requests.UserLoginByEmailReqDto;
import com.evergreen.evergreenmedic.dtos.response.RegisterUserRespDto;
import com.evergreen.evergreenmedic.dtos.response.UserLoginReqByEmailRespDto;
import com.evergreen.evergreenmedic.entities.OtpEntity;
import com.evergreen.evergreenmedic.entities.UserDetailEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.enums.user.UserGender;
import com.evergreen.evergreenmedic.implementations.CustomUserDetailsServiceImpl;
import com.evergreen.evergreenmedic.repositories.OtpRepository;
import com.evergreen.evergreenmedic.repositories.UserDetailRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;
    private final OtpRepository otpRepository;
    private static final int OTP_EXPIRY_IN_MINUTES = 1;

    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true if using HTTPS
        cookie.setPath("/"); // Set path to restrict the cookie usage scope
        cookie.setMaxAge(0); // Set expiry (in seconds, e.g., 1 day here)
        response.addCookie(cookie);
        return "success";
    }


    protected boolean isOtpExpired(OtpEntity otpEntity) {
        Instant currentInstant = Instant.now();
        Instant otpCreationInstant = otpEntity.getCreatedAt();
        long duration = Duration.between(otpCreationInstant, currentInstant).toMinutes();
        System.out.println("===========================");
        System.out.println("isOtpMatched (min) => " + duration);
        System.out.println("===========================");
        if (duration > OTP_EXPIRY_IN_MINUTES) {
            return true;
        } else {
            return false;
        }
    }

    public RegisterUserRespDto registerUser(RegisterUserReqDto registerUserReqDto) throws BadRequestException {
//        Extract DTO fields
        String firstName = registerUserReqDto.getFirstName();
        String lastName = registerUserReqDto.getLastName();
        String email = registerUserReqDto.getEmail();
        String password = registerUserReqDto.getPassword();
        String phoneNumber = registerUserReqDto.getPhoneNumber();
        UserGender gender = registerUserReqDto.getGender();
        Integer otpCode = registerUserReqDto.getOtp();
//        Verify Duplicate user
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity != null) {
            throw new BadRequestException("User already exists.");
        }
// Verify OTP
        OtpEntity otpEntity = otpRepository.findByOtpEmail(email);
        if (otpEntity == null) {
            throw new BadRequestException("Otp not found.");
        }

        if (!Objects.equals(otpEntity.getCode(), otpCode)) {
            throw new BadRequestException("Invalid Otp provided.");
        }
        if (this.isOtpExpired(otpEntity)) {
            throw new BadRequestException("Otp is expired.");
        }

// Create user
        userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setGender(gender);
//        Create user basic profile
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        userDetailEntity.setUser(userEntity);
        userDetailRepository.save(userDetailEntity);
        userEntity = userRepository.save(userEntity);


        RegisterUserRespDto registerUserRespDto = new RegisterUserRespDto();
        registerUserRespDto.setAccessToken(jwtUtil.generateToken(userEntity.getEmail()));
        registerUserRespDto.setUser(UserDto.mapToDto(userEntity));
        return registerUserRespDto;
    }

    public UserLoginReqByEmailRespDto loginByEmail(UserLoginByEmailReqDto userLoginByEmailReqDto, HttpServletResponse response) {

        String email = userLoginByEmailReqDto.getEmail();
        String password = userLoginByEmailReqDto.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String jwtToken = jwtUtil.generateToken(email);
        UserLoginReqByEmailRespDto userLoginRespDto = new UserLoginReqByEmailRespDto();

        userLoginRespDto.setAccessToken(jwtToken);
        userLoginRespDto.setUser(ProtectedUserDto.mapToDto(userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new)));
        Cookie cookie = new Cookie("accessToken", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true if using HTTPS
        cookie.setPath("/"); // Set path to restrict the cookie usage scope
//        cookie.setMaxAge(24 * 60 * 60); // Set expiry (in seconds, e.g., 1 day here)
        response.addCookie(cookie);
        return userLoginRespDto;

    }

    public ProtectedUserDto isAuthenticated(UserEntity userEntity) {
        System.out.println("=================================");
        System.out.println("=================================" + userEntity.toString());
        System.out.println("=================================");
        return ProtectedUserDto.mapToDto(userEntity);
    }
}
