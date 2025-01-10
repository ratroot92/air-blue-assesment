package com.evergreen.evergreenmedic.processors;

import com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement.PerformKycReqDto;
import com.evergreen.evergreenmedic.entities.kyc.KycRecord;
import com.evergreen.evergreenmedic.entities.kyc.KycRequirementField;
import com.evergreen.evergreenmedic.entities.kyc.KycVerification;
import com.evergreen.evergreenmedic.entities.kyc.KycVerificationField;
import com.evergreen.evergreenmedic.enums.kyc.KycVerificationStatus;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementName;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementVerificationMethod;
import com.evergreen.evergreenmedic.enums.kyc.kyc_requirement.KycRequirementVerificationProvider;
import com.evergreen.evergreenmedic.repositories.kyc.KycVerificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Component
@Slf4j
public class KycProcessor {

    private final com.evergreen.evergreenmedic.repositories.kyc.kycVerificationFieldRepository kycVerificationFieldRepository;
    private final KycVerificationRepository kycVerificationRepository;
    KycRecord kycRecord;
    KycVerification verification;


    public KycRecord process(KycRecord kycRecord, PerformKycReqDto performKycReqDto) {
        KycLevelName kycLevelName = kycRecord.getCurrentKycLevel().getLevelName();
        KycVerification kycVerification = performKycReqDto.getKycVerification();
        KycRequirementName kycRequirementName = kycVerification.getKycRequirement().getName();
        switch (kycLevelName) {
            case BASIC: {
                switch (kycRequirementName) {
                    case FULL_NAME_VERIFICATION: {
                        processFirstnameLastNameVerificationForBasicLevel(kycVerification);
                        return kycRecord;
                    }
                    default:
                        System.out.println("======================================");
                        System.out.println("CASE NOT HANDLED [kycRequirementName] " + kycRequirementName);
                        System.out.println("======================================");
                        return kycRecord;
                }
            }
            default:
                System.out.println("======================================");
                System.out.println("CASE NOT HANDLED [kycLevelName] " + kycLevelName);
                System.out.println("======================================");
                return kycRecord;
        }

    }

    protected KycVerification processFirstnameLastNameVerificationForBasicLevel(KycVerification kycVerification) {
        List<KycVerificationField> kycVerificationFields = new ArrayList<KycVerificationField>();

        for (KycRequirementField field : kycVerification.getKycRequirement().getKycRequirementFields()) {
            KycRequirementField reqBodyKycRequirementField = kycVerification.getKycRequirement().getKycRequirementFields().stream().filter(kycRequirementField -> Objects.equals(kycRequirementField.getId(), field.getId())).findFirst().orElseThrow(EntityNotFoundException::new);
//              create & populate verification fields
            KycVerificationField kycVerificationField = new KycVerificationField();
            kycVerificationField.setFieldName(field.getLabel());
            kycVerificationField.setFieldValue(reqBodyKycRequirementField.getValue());
            kycVerificationField.setKycRequirementField(field);
            kycVerificationField.setKycVerification(kycVerification);
            kycVerificationFields.add(kycVerificationField);
        }

        kycVerificationFields = kycVerificationFieldRepository.saveAll(kycVerificationFields);

        kycVerification.setKycVerificationFields(kycVerificationFields);
        kycVerification.setStatus(KycVerificationStatus.ATTEMPTED);
        kycVerification.setLocked(false);
        boolean isNextVerificationLocked = unLockNextVerification(kycVerification);
        if (isNextVerificationLocked) {
            log.info("User next verification is unlocked!.");
        }
        kycVerification = kycVerificationRepository.save(kycVerification);
        KycRequirementVerificationMethod kycRequirementVerificationMethod = kycVerification.getKycRequirement().getVerificationMethod();
        KycRequirementVerificationProvider kycRequirementVerificationProvider = kycVerification.getKycRequirement().getVerificationProvider();
        switch (kycRequirementVerificationProvider) {
            case VAULTSPAY: {
                switch (kycRequirementVerificationMethod) {
                    case VAULTSPAY_ADMIN_APPROVAL: {
                    }
                }
                break;
            }
        }
        return kycVerification;

    }

    protected boolean unLockNextVerification(KycVerification currentVerification) {
        int nextVerificationSeq = currentVerification.getSequence() + 1;
        KycVerification nextVerification = kycVerificationRepository.findBySequence(nextVerificationSeq).orElse(null);
        if (nextVerification != null) {
            nextVerification.setLocked(true);
            kycVerificationRepository.save(nextVerification);
            return true;
        } else {
            return false;
        }


    }

}
