package com.evergreen.evergreenmedic.repositories.kyc;


import com.evergreen.evergreenmedic.entities.kyc.KycRecordLevelHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRecordLevelHistoryRepository extends JpaRepository<KycRecordLevelHistory, Integer> {
}
