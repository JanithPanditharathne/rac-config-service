package com.zone24x7.rac.configservice.recslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecSlotRuleRepository extends JpaRepository<RecSlotRule, Integer> {
    List<RecSlotRule> findAllByRecSlotID(int id);
    List<RecSlotRule> findAllByRuleID(int id);
}
