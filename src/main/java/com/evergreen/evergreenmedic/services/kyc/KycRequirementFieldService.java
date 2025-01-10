package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRequirement;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementField;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldNote;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldValidation;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementFieldType;
import com.evergreen.evergreenmedic.repositories.kyc.KycRequirementFieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycRequirementFieldService {

    private final KycRequirementFieldRepository kycRequirementFieldRepository;
    private final KycRequirementFieldNoteService kycRequirementFieldNoteService;
    private final KycRequirementFieldValidationService kycRequirementFieldValidationService;

    public KycRequirementFieldService(KycRequirementFieldRepository kycRequirementFieldRepository, KycRequirementFieldNoteService kycRequirementFieldNoteService, KycRequirementFieldValidationService kycRequirementFieldValidationService) {
        this.kycRequirementFieldRepository = kycRequirementFieldRepository;
        this.kycRequirementFieldNoteService = kycRequirementFieldNoteService;
        this.kycRequirementFieldValidationService = kycRequirementFieldValidationService;
    }

    public KycRequirementField getFirstnameField(KycRequirement kycRequirement) {

        KycRequirementField requirementField = new KycRequirementField();
        requirementField.setLabel("Firstname");
        requirementField.setType(KycRequirementFieldType.TEXT);
        requirementField.setKycRequirement(kycRequirement);
        requirementField = kycRequirementFieldRepository.save(requirementField);

        List<KycRequirementFieldNote> fieldNotes = kycRequirementFieldNoteService.getFirstNameNotes(requirementField);
        requirementField.setNotes(fieldNotes);

        List<KycRequirementFieldValidation> fieldValidations = kycRequirementFieldValidationService.getFirstnameValidations(requirementField);
        requirementField.setKycRequirementFieldValidations(fieldValidations);

        return requirementField;
    }

    public KycRequirementField getLastnameField(KycRequirement kycRequirement) {
        KycRequirementField requirementField = new KycRequirementField();
        requirementField.setLabel("Lastname");
        requirementField.setType(KycRequirementFieldType.TEXT);
        requirementField.setKycRequirement(kycRequirement);
        requirementField = kycRequirementFieldRepository.save(requirementField);

        List<KycRequirementFieldNote> fieldNotes = kycRequirementFieldNoteService.getLastNameNotes(requirementField);
        requirementField.setNotes(fieldNotes);

        List<KycRequirementFieldValidation> fieldValidations = kycRequirementFieldValidationService.getLastNameValidations(requirementField);
        requirementField.setKycRequirementFieldValidations(fieldValidations);

        return requirementField;
    }

    public KycRequirementField getProofDocField(KycRequirement kycRequirement) {

        KycRequirementField requirementField = new KycRequirementField();
        requirementField.setLabel("Document upload");
        requirementField.setType(KycRequirementFieldType.DOCUMENT);
        requirementField.setKycRequirement(kycRequirement);
        requirementField = kycRequirementFieldRepository.save(requirementField);

        List<KycRequirementFieldNote> fieldNotes = kycRequirementFieldNoteService.getProofDocNotes(requirementField);
        requirementField.setNotes(fieldNotes);

        List<KycRequirementFieldValidation> fieldValidations = kycRequirementFieldValidationService.getProofDocValidations(requirementField);
        requirementField.setKycRequirementFieldValidations(fieldValidations);

        return requirementField;
    }


    public KycRequirementField getEmailField(KycRequirement kycRequirement) {

        KycRequirementField requirementField = new KycRequirementField();
        requirementField.setLabel("email");
        requirementField.setType(KycRequirementFieldType.OTP_EMAIL);
        requirementField.setKycRequirement(kycRequirement);
        requirementField = kycRequirementFieldRepository.save(requirementField);

        List<KycRequirementFieldNote> fieldNotes = kycRequirementFieldNoteService.getEmailNotes(requirementField);
        requirementField.setNotes(fieldNotes);

        List<KycRequirementFieldValidation> fieldValidations = kycRequirementFieldValidationService.getEmailValidations(requirementField);
        requirementField.setKycRequirementFieldValidations(fieldValidations);

        return requirementField;
    }

    public KycRequirementField getPhoneField(KycRequirement kycRequirement) {
        KycRequirementField requirementField = new KycRequirementField();
        requirementField.setLabel("phone");
        requirementField.setType(KycRequirementFieldType.OTP_PHONE);
        requirementField.setKycRequirement(kycRequirement);
        requirementField = kycRequirementFieldRepository.save(requirementField);

        List<KycRequirementFieldNote> fieldNotes = kycRequirementFieldNoteService.getPhoneNotes(requirementField);
        requirementField.setNotes(fieldNotes);

        List<KycRequirementFieldValidation> fieldValidations = kycRequirementFieldValidationService.getPhoneValidation(requirementField);
        requirementField.setKycRequirementFieldValidations(fieldValidations);

        return requirementField;
    }
}
