package com.evergreen.evergreenmedic;

import com.evergreen.evergreenmedic.dtos.requests.RegisterUserReqDto;
import com.evergreen.evergreenmedic.dtos.requests.otp.SenOtpReqDto;
import com.evergreen.evergreenmedic.enums.OtpValidFor;
import com.evergreen.evergreenmedic.enums.user.UserGender;
import com.evergreen.evergreenmedic.services.AuthService;
import com.evergreen.evergreenmedic.services.OtpService;
import com.evergreen.evergreenmedic.services.RoleService;
import com.evergreen.evergreenmedic.services.kyc.KycLevelService;
import com.evergreen.evergreenmedic.services.kyc.KycRequirementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.evergreen.evergreenmedic.entities")
public class EvergreenmedicApplication {


    public static void main(String[] args) {
        SpringApplication.run(EvergreenmedicApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
//
            String email = "maliksblr92@gmail.com";
            String password = "ksby871FBLS*^@3";
//
            SenOtpReqDto senOtpReqDto = new SenOtpReqDto();
            senOtpReqDto.setEmail(email);
            senOtpReqDto.setOtpValidFor(OtpValidFor.REGISTER);
            OtpService otpService = context.getBean(OtpService.class);
//            otpService.sendOtp(senOtpReqDto);
            RegisterUserReqDto registerUserReqDto = new RegisterUserReqDto();
            registerUserReqDto.setFirstName("Ahmad");
            registerUserReqDto.setLastName("Kabeer");
            registerUserReqDto.setEmail(email);
            registerUserReqDto.setPassword(password);
            registerUserReqDto.setOtp(111111);
            registerUserReqDto.setGender(UserGender.MALE);
            registerUserReqDto.setPhoneNumber("923441500542");

            AuthService authService = context.getBean(AuthService.class);
            authService.registerUser(registerUserReqDto);


//            seed roles
            RoleService roleService = context.getBean(RoleService.class);
            roleService.seed();
//            seed kyc-levels
            KycLevelService kycLevelService = context.getBean(KycLevelService.class);
            kycLevelService.seed();
//            seed kyc-requirements
            KycRequirementService kycRequirementService = context.getBean(KycRequirementService.class);
            kycRequirementService.seedRequirementsForBasicLevel();

        };
    }
}
