package com.zone24x7.rac.configservice.recslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecSlotRepository extends JpaRepository<RecSlot, Integer> {
}
