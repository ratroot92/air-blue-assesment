package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement.PerformKycReqDto;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.entities.kyc.*;
import com.evergreen.evergreenmedic.enums.UserRole;
import com.evergreen.evergreenmedic.enums.kyc.KycRecordStatus;
import com.evergreen.evergreenmedic.enums.kyc.KycVerificationStatus;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.repositories.kyc.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KycRecordService {
    private final UserRepository userRepository;
    private final KycLevelRepository kycLevelRepository;
    private final KycRecordRepository kycRecordRepository;
    private final KycVerificationRepository kycVerificationRepository;
    private final KycRecordLevelHistoryRepository kycRecordLevelHistoryRepository;
    private final com.evergreen.evergreenmedic.repositories.kyc.kycVerificationFieldRepository kycVerificationFieldRepository;

    public KycRecordService(UserRepository userRepository, KycLevelRepository kycLevelRepository, KycRecordRepository kycRecordRepository, KycVerificationRepository kycVerificationRepository, KycRecordLevelHistoryRepository kycRecordLevelHistoryRepository, kycVerificationFieldRepository kycVerificationFieldRepository) {
        this.userRepository = userRepository;
        this.kycLevelRepository = kycLevelRepository;
        this.kycRecordRepository = kycRecordRepository;
        this.kycVerificationRepository = kycVerificationRepository;
        this.kycRecordLevelHistoryRepository = kycRecordLevelHistoryRepository;
        this.kycVerificationFieldRepository = kycVerificationFieldRepository;
    }

    public KycRecord performKyc(PerformKycReqDto performKycReqDto) throws JsonProcessingException {
        int kycRecordId = performKycReqDto.getKycRecordId();

        KycVerification reqKycVerification = new ObjectMapper().readValue(performKycReqDto.getKycVerification(), KycVerification.class);

        KycRecord kycRecord = kycRecordRepository.findById(kycRecordId).orElseThrow(EntityNotFoundException::new);

        KycVerification subjectedKycVerificationFromDb = kycRecord.getKycVerifications().stream().filter(kycVerification -> Objects.equals(kycVerification.getId(), reqKycVerification.getId())).findFirst().orElseThrow(EntityNotFoundException::new);

        if (subjectedKycVerificationFromDb.getKycVerificationFields().isEmpty()) {

            List<KycVerificationField> kycVerificationFields = new ArrayList<KycVerificationField>();

            for (KycRequirementField field : subjectedKycVerificationFromDb.getKycRequirement().getKycRequirementFields()) {

                KycRequirementField reqBodyKycRequirementField = reqKycVerification.getKycRequirement().getKycRequirementFields().stream().filter(kycRequirementField -> Objects.equals(kycRequirementField.getId(), field.getId())).findFirst().orElseThrow(EntityNotFoundException::new);
//              create & populate verification fields
                KycVerificationField kycVerificationField = new KycVerificationField();
                kycVerificationField.setFieldName(field.getLabel());
                kycVerificationField.setFieldValue(reqBodyKycRequirementField.getValue());
                kycVerificationField.setKycRequirementField(field);
                kycVerificationField.setKycVerification(subjectedKycVerificationFromDb);
                kycVerificationFields.add(kycVerificationField);
            }

            kycVerificationFields = kycVerificationFieldRepository.saveAll(kycVerificationFields);

            subjectedKycVerificationFromDb.setKycVerificationFields(kycVerificationFields);
            subjectedKycVerificationFromDb.setStatus(KycVerificationStatus.ATTEMPTED);


            List<KycVerification> updatedKycVerifications = kycRecord.getKycVerifications().stream().map(kycVerification -> {
                if (Objects.equals(kycVerification.getId(), subjectedKycVerificationFromDb.getId())) {
                    return subjectedKycVerificationFromDb;
                }
                return kycVerification;
            }).collect(Collectors.toList());

            kycRecord.setKycVerifications(updatedKycVerifications);
            kycRecordRepository.save(kycRecord);
            return kycRecord;
        } else {
            return kycRecord;

        }


    }

    public KycRecord init(UserEntity userEntity) {

        if (userEntity.getKycRecord() == null) {

            UserRole userRole = userEntity.getRole();
            KycLevel userKycLevel = kycLevelRepository.findByLevelName(KycLevelName.BASIC).orElseThrow(() -> new EntityNotFoundException("Kyc level not found."));

//           create kyc record start
            KycRecord userNewKycRecord = new KycRecord();
            userNewKycRecord.setUser(userEntity);
            userNewKycRecord.setStatus(KycRecordStatus.PENDING);
            userNewKycRecord.setCurrentKycLevel(userKycLevel);
            userNewKycRecord = kycRecordRepository.save(userNewKycRecord);
//           create kyc record end

//            populate kyc history start
            KycRecordLevelHistory kycHistory = new KycRecordLevelHistory();
            kycHistory.setKycLevel(userKycLevel);
            kycHistory.setComments(userEntity.getFirstName() + " has been assigned to " + userKycLevel.getLevelName().toString() + " level.");
            kycHistory.setKycRecord(userNewKycRecord);
            userEntity.setKycRecord(userNewKycRecord);
            userRepository.save(userEntity);
            userNewKycRecord.setKycHistory(List.of(kycRecordLevelHistoryRepository.save(kycHistory)));
//            populate kyc history end

//            populate kyc verifications start
            List<KycVerification> kycVerifications = new ArrayList<KycVerification>();
            for (KycRequirement kycRequirement : userKycLevel.getKycRequirements()) {
                KycVerification kycVerification = new KycVerification();
                kycVerification.setKycRequirement(kycRequirement);
                kycVerification.setKycRecord(userNewKycRecord);
                kycVerification = kycVerificationRepository.save(kycVerification);
                kycVerifications.add(kycVerification);
            }
            userNewKycRecord.setKycVerifications(kycVerifications);
//            populate kyc verifications end

            return userNewKycRecord;
        } else {
            return userEntity.getKycRecord();
        }
    }


}
