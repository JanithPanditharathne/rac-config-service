package com.zone24x7.rac.configservice.recslot.recslotrule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecSlotRuleRepository extends JpaRepository<RecSlotRule, Integer> {

    /**
     * Get all rec slot rule associations by rec slot ID.
     *
     * @param recSlotID Rec slot ID
     * @return          List of RecSlotRule
     */
    List<RecSlotRule> findAllByRecSlotID(int recSlotID);
}
