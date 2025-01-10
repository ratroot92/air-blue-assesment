package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycLevel;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirement;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementField;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.*;
import com.evergreen.evergreenmedic.repositories.kyc.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycRequirementService {
    private final KycLevelRepository kycLevelRepository;
    private final KycRequirementRepository kycRequirementRepository;
    private final KycRequirementFieldService kycRequirementFieldService;

    public KycRequirementService(KycLevelRepository kycLevelRepository, KycRequirementFieldValidationRepository kycRequirementFieldValidationRepository, KycRequirementFieldRepository kycRequirementFieldRepository, KycRequirementRepository kycRequirementRepository, KycRecordRepository kycRecordRepository, KycRequirementFieldNoteRepository kycRequirementFieldNoteRepository, KycRequirementFieldNoteService kycRequirementFieldNoteService, KycRequirementFieldService kycRequirementFieldService, KycRequirementFieldValidationService kycRequirementFieldValidationService) {
        this.kycLevelRepository = kycLevelRepository;
        this.kycRequirementRepository = kycRequirementRepository;
        this.kycRequirementFieldService = kycRequirementFieldService;
    }

    public List<KycRequirement> getAllKycRequirements() {
        return kycRequirementRepository.findAll();
    }

    public KycRequirement getBasicLevelNameKycRequirement() {

        KycLevel basicKycLevel = kycLevelRepository.findByLevelName(KycLevelName.BASIC).orElseThrow(() -> new EntityNotFoundException("Kyc level not found."));
        KycRequirement firstNameLastNameRequirement = new KycRequirement();
        firstNameLastNameRequirement.setKycLevel(basicKycLevel);
        firstNameLastNameRequirement.setName(KycRequirementName.FULL_NAME_VERIFICATION);
        firstNameLastNameRequirement.setDisplayName("First Name & Last Name Verification");
        firstNameLastNameRequirement.setDescription("Dear user, you are required to provide your first name and last name, along with uploading a proof document for verification. The document can be a CNIC, birth certificate, or any other relevant document.");
        firstNameLastNameRequirement.setVerificationMethod(KycRequirementVerificationMethod.VAULTSPAY_ADMIN_APPROVAL);
        firstNameLastNameRequirement.setVerificationProvider(KycRequirementVerificationProvider.VAULTSPAY);
        firstNameLastNameRequirement.setSequence(1);
        firstNameLastNameRequirement.setActionType(KycRequirementActionType.SUBMIT_FORM);
        firstNameLastNameRequirement.setActionUri(KycRequirementActionUri.FIRSTNAME_LASTNAME_VERIFICATION_URI.getUri());
        firstNameLastNameRequirement = kycRequirementRepository.save(firstNameLastNameRequirement);

        KycRequirementField firstNameRequirementField = kycRequirementFieldService.getFirstnameField(firstNameLastNameRequirement);

        KycRequirementField lastNameRequirementField = kycRequirementFieldService.getLastnameField(firstNameLastNameRequirement);

        KycRequirementField proofDocRequirementField = kycRequirementFieldService.getProofDocField(firstNameLastNameRequirement);


        List<KycRequirementField> kycRequirementFieldsList = List.of(firstNameRequirementField, lastNameRequirementField, proofDocRequirementField);

        firstNameLastNameRequirement.setKycRequirementFields(kycRequirementFieldsList);

        return firstNameLastNameRequirement;
    }

    public KycRequirement getBasicLevelEmailKycRequirement() {

        KycLevel basicKycLevel = kycLevelRepository.findByLevelName(KycLevelName.BASIC).orElseThrow(() -> new EntityNotFoundException("Kyc level not found."));

        KycRequirement emailVerificationRequirement = new KycRequirement();
        emailVerificationRequirement.setVerificationMethod(KycRequirementVerificationMethod.VAULTSPAY_ADMIN_APPROVAL);
        emailVerificationRequirement.setDescription("Email Verification requires you to validate your email address. An OTP (One-Time Password) will be sent to your registered email address, which you must enter to complete the verification process.");
        emailVerificationRequirement.setDisplayName("Email Verification");
        emailVerificationRequirement.setName(KycRequirementName.EMAIL_VERIFICATION);
        emailVerificationRequirement.setVerificationProvider((KycRequirementVerificationProvider.VAULTSPAY));
        emailVerificationRequirement.setKycLevel(basicKycLevel);
        emailVerificationRequirement.setSequence(2);
        emailVerificationRequirement.setActionType(KycRequirementActionType.SUBMIT_FORM);
        emailVerificationRequirement.setActionUri(KycRequirementActionUri.SEND_EMAIL_OTP.getUri());
        emailVerificationRequirement = kycRequirementRepository.save(emailVerificationRequirement);

        KycRequirementField emailKycRequirementField = kycRequirementFieldService.getEmailField(emailVerificationRequirement);

        emailVerificationRequirement.setKycRequirementFields(List.of(emailKycRequirementField));

        return emailVerificationRequirement;

    }

    public KycRequirement getBasicLevelPhoneRequirement() {
        KycLevel basicKycLevel = kycLevelRepository.findByLevelName(KycLevelName.BASIC).orElseThrow(() -> new EntityNotFoundException("Kyc level not found."));
        KycRequirement phoneVerificationRequirement = new KycRequirement();
        phoneVerificationRequirement.setKycLevel(basicKycLevel);
        phoneVerificationRequirement.setDescription("Phone Number Verification requires you to validate your registered phone number. An OTP (One-Time Password) will be sent via SMS to your phone number, which you must enter to complete the verification process");
        phoneVerificationRequirement.setDisplayName("Phone Number Verification");

        phoneVerificationRequirement.setName(KycRequirementName.PHONE_NUMBER_VERIFICATION);
        phoneVerificationRequirement.setVerificationMethod(KycRequirementVerificationMethod.VAULTSPAY_ADMIN_APPROVAL);
        phoneVerificationRequirement.setVerificationProvider(KycRequirementVerificationProvider.VAULTSPAY);
        phoneVerificationRequirement.setSequence(3);
        phoneVerificationRequirement.setActionType(KycRequirementActionType.SUBMIT_FORM);
        phoneVerificationRequirement.setActionUri(KycRequirementActionUri.SEND_EMAIL_OTP.getUri());
        phoneVerificationRequirement = kycRequirementRepository.save(phoneVerificationRequirement);

        //        RequirementFields
        KycRequirementField kycRequirementField = kycRequirementFieldService.getPhoneField(phoneVerificationRequirement);
        phoneVerificationRequirement.setKycRequirementFields(List.of(kycRequirementField));


//
        return phoneVerificationRequirement;
    }

    public void seedRequirementsForBasicLevel() {
        KycRequirement basicLevelPhoneKycRequirementEntity = getBasicLevelPhoneRequirement();
        KycRequirement basicLevelEmailKycRequirement = getBasicLevelEmailKycRequirement();
        KycRequirement basicLevelNameKycRequirement = getBasicLevelNameKycRequirement();


    }
}
