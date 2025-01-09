package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.dtos.requests.otp.SenOtpReqDto;
import com.evergreen.evergreenmedic.entities.OtpEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.enums.OtpType;
import com.evergreen.evergreenmedic.repositories.OtpRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.services.smtp.EmailService;
import com.evergreen.evergreenmedic.smtp.EmailDetails;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public OtpService(UserRepository userRepository, OtpRepository otpRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    public boolean sendOtp(SenOtpReqDto senOtpReqDto) throws BadRequestException {
        String email = senOtpReqDto.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity != null) {
            throw new BadRequestException("User already exist");
        } else {
            OtpEntity newOtpEntity = new OtpEntity();
            newOtpEntity.setOtpEmail(email);
            newOtpEntity.setOtpType(OtpType.EMAIL);
            newOtpEntity.setCode(111111);
            otpRepository.save(newOtpEntity);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("REGISTRATION OTP");
            emailDetails.setRecipient(email);
            emailDetails.setMsgBody("Dear EvergreenMedic User, your request to register your email address account has been received. To verify, please enter the verification code: " + newOtpEntity.getCode().toString() + " in the EvergreenMedic app.\n" +
                    "For any issues, please contact us on 3737 (For EvergreenMedic Users) and 042-111-00-3737 (For Other Networks).\n" +
                    "\n");
            boolean isMailSent = emailService.sendSimpleMail(emailDetails);
            if (isMailSent) {
                return true;
            } else {
                return false;
            }
        }


    }
}
