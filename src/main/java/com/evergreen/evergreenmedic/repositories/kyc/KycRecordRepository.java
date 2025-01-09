package com.evergreen.evergreenmedic.repositories.kyc;

import com.evergreen.evergreenmedic.entities.kyc.KycRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRecordRepository extends JpaRepository<KycRecord, Integer> {
}
