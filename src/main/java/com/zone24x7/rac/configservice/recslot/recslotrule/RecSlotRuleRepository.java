package com.zone24x7.rac.configservice.recslot.recslotrule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecSlotRuleRepository extends JpaRepository<RecSlotRule, Integer> {
}
