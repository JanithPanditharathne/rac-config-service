package com.zone24x7.rac.configservice.recslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecSlotRepository extends JpaRepository<RecSlot, Integer> {
    List<RecSlot> findAllByOrderByIdDesc();
    List<RecSlot> findAllByRecID(int id);
}
