package com.zone24x7.rac.configservice.actiontrace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTraceRepository extends JpaRepository<ActionTrace, Integer> {
}
