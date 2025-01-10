package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.dtos.requests.kyc.kyc_requirement.PerformKycReqDto;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.entities.kyc.*;
import com.evergreen.evergreenmedic.enums.UserRole;
import com.evergreen.evergreenmedic.enums.kyc.KycRecordStatus;
import com.evergreen.evergreenmedic.enums.kyc.KycVerificationStatus;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.processors.KycProcessor;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.repositories.kyc.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class KycRecordService {
    private final UserRepository userRepository;
    private final KycLevelRepository kycLevelRepository;
    private final KycRecordRepository kycRecordRepository;
    private final KycVerificationRepository kycVerificationRepository;
    private final KycRecordLevelHistoryRepository kycRecordLevelHistoryRepository;
    private final com.evergreen.evergreenmedic.repositories.kyc.kycVerificationFieldRepository kycVerificationFieldRepository;
    private final KycProcessor kycProcessor;

    public KycRecordService(UserRepository userRepository, KycLevelRepository kycLevelRepository, KycRecordRepository kycRecordRepository, KycVerificationRepository kycVerificationRepository, KycRecordLevelHistoryRepository kycRecordLevelHistoryRepository, kycVerificationFieldRepository kycVerificationFieldRepository, KycProcessor kycProcessor) {
        this.userRepository = userRepository;
        this.kycLevelRepository = kycLevelRepository;
        this.kycRecordRepository = kycRecordRepository;
        this.kycVerificationRepository = kycVerificationRepository;
        this.kycRecordLevelHistoryRepository = kycRecordLevelHistoryRepository;
        this.kycVerificationFieldRepository = kycVerificationFieldRepository;
        this.kycProcessor = kycProcessor;
    }


    public KycRecord performKyc(PerformKycReqDto performKycReqDto) throws JsonProcessingException, BadRequestException {
        int kycRecordId = performKycReqDto.getKycRecordId();
//        KycVerification reqKycVerification = new ObjectMapper().readValue(performKycReqDto.getKycVerification(), KycVerification.class);
        KycRecord kycRecord = kycRecordRepository.findById(kycRecordId).orElseThrow(EntityNotFoundException::new);
        KycVerification subjectedKycVerificationFromDb = kycRecord.getKycVerifications().stream().filter(kycVerification -> Objects.equals(kycVerification.getId(), performKycReqDto.getKycVerification().getId())).findFirst().orElseThrow(EntityNotFoundException::new);
        if (subjectedKycVerificationFromDb.getStatus() == KycVerificationStatus.ATTEMPTED) {
            throw new BadRequestException("Verification already attempted. Please wait for approval by admin.");
        }
        return kycProcessor.process(kycRecord, performKycReqDto);


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
            boolean isFirstIteration = true;
            for (KycRequirement kycRequirement : userKycLevel.getKycRequirements()) {
                KycVerification kycVerification = new KycVerification();
                kycVerification.setKycRequirement(kycRequirement);
                kycVerification.setKycRecord(userNewKycRecord);
                kycVerification.setLocked(isFirstIteration);
                kycVerification.setSequence(kycRequirement.getSequence());
                isFirstIteration = false;
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
