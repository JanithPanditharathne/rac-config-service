package com.zone24x7.rac.configservice.recslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecSlotRuleRepository extends JpaRepository<RecSlotRule, Integer> {

    /**
     * Get all rec slot rule associations by rec slot id.
     *
     * @param recSlotID Rec slot id
     * @return          List of RecSlotRule
     */
    List<RecSlotRule> findAllByRecSlotID(int recSlotID);
}
