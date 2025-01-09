package com.evergreen.evergreenmedic.services.kyc;


import com.evergreen.evergreenmedic.entities.kyc.KycRequirementField;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldValidation;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementFieldValidationType;
import com.evergreen.evergreenmedic.repositories.kyc.KycRequirementFieldValidationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycRequirementFieldValidationService {


    private final KycRequirementFieldValidationRepository kycRequirementFieldValidationRepository;

    public KycRequirementFieldValidationService(KycRequirementFieldValidationRepository kycRequirementFieldValidationRepository) {
        this.kycRequirementFieldValidationRepository = kycRequirementFieldValidationRepository;
    }

    public List<KycRequirementFieldValidation> getFirstnameValidations(KycRequirementField firstNameRequirementField) {

        KycRequirementFieldValidation firstNameRequiredValidation = new KycRequirementFieldValidation();
        firstNameRequiredValidation.setType(KycRequirementFieldValidationType.REQUIRED);
        firstNameRequiredValidation.setValue("true");
        firstNameRequiredValidation.setKycRequirementField(firstNameRequirementField);
        firstNameRequiredValidation = kycRequirementFieldValidationRepository.save(firstNameRequiredValidation);


        KycRequirementFieldValidation firstNameMinValidation = new KycRequirementFieldValidation();
        firstNameMinValidation.setType(KycRequirementFieldValidationType.MIN);
        firstNameMinValidation.setValue("3");
        firstNameMinValidation.setKycRequirementField(firstNameRequirementField);
        firstNameMinValidation = kycRequirementFieldValidationRepository.save(firstNameMinValidation);


        KycRequirementFieldValidation firstNameMaxValidation = new KycRequirementFieldValidation();
        firstNameMaxValidation.setType(KycRequirementFieldValidationType.MIN);
        firstNameMaxValidation.setValue("10");
        firstNameMaxValidation.setKycRequirementField(firstNameRequirementField);
        firstNameMaxValidation = kycRequirementFieldValidationRepository.save(firstNameMaxValidation);

        return List.of(firstNameRequiredValidation, firstNameMinValidation, firstNameMaxValidation);
    }

    public List<KycRequirementFieldValidation> getLastNameValidations(KycRequirementField lastNameRequirementField) {

        KycRequirementFieldValidation lastNameRequiredvalidation = new KycRequirementFieldValidation();
        lastNameRequiredvalidation.setType(KycRequirementFieldValidationType.REQUIRED);
        lastNameRequiredvalidation.setValue("true");
        lastNameRequiredvalidation.setKycRequirementField(lastNameRequirementField);
        lastNameRequiredvalidation = kycRequirementFieldValidationRepository.save(lastNameRequiredvalidation);

        KycRequirementFieldValidation lastNameMinValidation = new KycRequirementFieldValidation();
        lastNameMinValidation.setType(KycRequirementFieldValidationType.MIN);
        lastNameMinValidation.setValue("3");
        lastNameMinValidation.setKycRequirementField(lastNameRequirementField);
        lastNameMinValidation = kycRequirementFieldValidationRepository.save(lastNameMinValidation);


        KycRequirementFieldValidation lastNameMaxValidation = new KycRequirementFieldValidation();
        lastNameMaxValidation.setType(KycRequirementFieldValidationType.MIN);
        lastNameMaxValidation.setValue("10");
        lastNameMaxValidation.setKycRequirementField(lastNameRequirementField);
        lastNameMaxValidation = kycRequirementFieldValidationRepository.save(lastNameMaxValidation);

        return List.of(lastNameRequiredvalidation, lastNameMinValidation, lastNameMaxValidation);
    }

    public List<KycRequirementFieldValidation> getProofDocValidations(KycRequirementField firstNameLastNameProofDocRequirement) {

        KycRequirementFieldValidation documentUploadRequiredValidation = new KycRequirementFieldValidation();
        documentUploadRequiredValidation.setType(KycRequirementFieldValidationType.REQUIRED);
        documentUploadRequiredValidation.setValue("true");
        documentUploadRequiredValidation.setKycRequirementField(firstNameLastNameProofDocRequirement);
        documentUploadRequiredValidation = kycRequirementFieldValidationRepository.save(documentUploadRequiredValidation);

        KycRequirementFieldValidation documentUploadMinValidation = new KycRequirementFieldValidation();
        documentUploadMinValidation.setType(KycRequirementFieldValidationType.MIN);
        documentUploadMinValidation.setValue("1");
        documentUploadMinValidation.setKycRequirementField(firstNameLastNameProofDocRequirement);
        documentUploadMinValidation = kycRequirementFieldValidationRepository.save(documentUploadMinValidation);
        KycRequirementFieldValidation documentUploadMaxValidation = new KycRequirementFieldValidation();

        documentUploadMaxValidation.setType(KycRequirementFieldValidationType.MAX);
        documentUploadMaxValidation.setValue("2");
        documentUploadMaxValidation.setKycRequirementField(firstNameLastNameProofDocRequirement);
        documentUploadMaxValidation = kycRequirementFieldValidationRepository.save(documentUploadMaxValidation);

        return List.of(documentUploadRequiredValidation, documentUploadMinValidation, documentUploadMaxValidation);
    }

    public List<KycRequirementFieldValidation> getEmailValidations(KycRequirementField kycRequirementField) {
        KycRequirementFieldValidation requiredEmailValidation = new KycRequirementFieldValidation();
        requiredEmailValidation.setType(KycRequirementFieldValidationType.REQUIRED);
        requiredEmailValidation.setValue("true");
        requiredEmailValidation.setKycRequirementField(kycRequirementField);
        requiredEmailValidation = kycRequirementFieldValidationRepository.save(requiredEmailValidation);

        KycRequirementFieldValidation minEmailValidation = new KycRequirementFieldValidation();
        minEmailValidation.setType(KycRequirementFieldValidationType.MIN);
        minEmailValidation.setValue("true");
        minEmailValidation.setKycRequirementField(kycRequirementField);

        minEmailValidation = kycRequirementFieldValidationRepository.save(minEmailValidation);

        KycRequirementFieldValidation maxEmailValidation = new KycRequirementFieldValidation();
        maxEmailValidation.setType(KycRequirementFieldValidationType.MAX);
        maxEmailValidation.setValue("true");
        maxEmailValidation.setKycRequirementField(kycRequirementField);

        maxEmailValidation = kycRequirementFieldValidationRepository.save(maxEmailValidation);

        return List.of(requiredEmailValidation, minEmailValidation, maxEmailValidation);
    }

    public List<KycRequirementFieldValidation> getPhoneValidation(KycRequirementField kycRequirementField) {

        KycRequirementFieldValidation requiredPhoneValidation = new KycRequirementFieldValidation();
        requiredPhoneValidation.setType(KycRequirementFieldValidationType.REQUIRED);
        requiredPhoneValidation.setValue("true");
        requiredPhoneValidation.setKycRequirementField(kycRequirementField);
        requiredPhoneValidation = kycRequirementFieldValidationRepository.save(requiredPhoneValidation);

        KycRequirementFieldValidation maxPhoneLengthValidation = new KycRequirementFieldValidation();
        maxPhoneLengthValidation.setType(KycRequirementFieldValidationType.MAX);
        maxPhoneLengthValidation.setValue("11");
        maxPhoneLengthValidation.setKycRequirementField(kycRequirementField);
        maxPhoneLengthValidation = kycRequirementFieldValidationRepository.save(maxPhoneLengthValidation);

        KycRequirementFieldValidation minPhoneLengthValidation = new KycRequirementFieldValidation();
        minPhoneLengthValidation.setType(KycRequirementFieldValidationType.MAX);
        minPhoneLengthValidation.setValue("11");
        minPhoneLengthValidation.setKycRequirementField(kycRequirementField);
        minPhoneLengthValidation = kycRequirementFieldValidationRepository.save(minPhoneLengthValidation);

        return List.of(requiredPhoneValidation, maxPhoneLengthValidation, minPhoneLengthValidation);
    }
}
