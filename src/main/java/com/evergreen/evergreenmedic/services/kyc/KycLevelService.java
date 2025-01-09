package com.evergreen.evergreenmedic.services.kyc;

import com.evergreen.evergreenmedic.entities.RoleEntity;
import com.evergreen.evergreenmedic.entities.kyc.KycLevel;
import com.evergreen.evergreenmedic.enums.UserRole;
import com.evergreen.evergreenmedic.enums.kyc.kyc_level.KycLevelName;
import com.evergreen.evergreenmedic.repositories.RoleRepository;
import com.evergreen.evergreenmedic.repositories.kyc.KycLevelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycLevelService {
    private final RoleRepository roleRepository;
    KycLevelRepository kycLevelRepository;

    public KycLevelService(KycLevelRepository kycLevelRepository, RoleRepository roleRepository) {
        this.kycLevelRepository = kycLevelRepository;
        this.roleRepository = roleRepository;
    }

    public List<KycLevel> getAllKycLevels() {
        return kycLevelRepository.findAll();
    }

    public List<KycLevel> seed() {

        RoleEntity agentRoleEntity = roleRepository.findByName(UserRole.AGENT).orElseThrow(() -> new EntityNotFoundException("Role not found."));
        RoleEntity customerRoleEntity = roleRepository.findByName(UserRole.CUSTOMER).orElseThrow(() -> new EntityNotFoundException("Role not found."));

//        Basic Level
        KycLevel basicKycLevel = new KycLevel();
        basicKycLevel.setLevelName(KycLevelName.BASIC);
        basicKycLevel.setDescription("Basic kyc level");
        basicKycLevel.setRequiredForUserRoleEntities(List.of(agentRoleEntity, customerRoleEntity));
        kycLevelRepository.save(basicKycLevel);

//        Advance Level
        KycLevel advanceKycLevel = new KycLevel();
        advanceKycLevel.setLevelName(KycLevelName.ADVANCE);
        advanceKycLevel.setDescription("Advance kyc level");
        basicKycLevel.setRequiredForUserRoleEntities(List.of(customerRoleEntity));

        kycLevelRepository.save(advanceKycLevel);


//        Business Level
        KycLevel businessKycLevel = new KycLevel();
        businessKycLevel.setLevelName(KycLevelName.BUSINESS);
        businessKycLevel.setDescription("Business kyc level");
        basicKycLevel.setRequiredForUserRoleEntities(List.of(agentRoleEntity));

        kycLevelRepository.save(businessKycLevel);

//        Legal Level
        KycLevel legalKycLevel = new KycLevel();
        legalKycLevel.setLevelName(KycLevelName.LEGAL);
        legalKycLevel.setDescription("Legal kyc level");
        basicKycLevel.setRequiredForUserRoleEntities(List.of(agentRoleEntity, customerRoleEntity));
        kycLevelRepository.save(legalKycLevel);

        return List.of(basicKycLevel, advanceKycLevel, businessKycLevel, legalKycLevel);
    }
}
