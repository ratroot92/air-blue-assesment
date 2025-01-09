package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRequirementFieldNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KycRequirementFieldNoteRepository extends JpaRepository<KycRequirementFieldNote, UUID> {
}
