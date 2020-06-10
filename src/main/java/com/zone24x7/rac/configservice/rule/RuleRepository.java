package com.zone24x7.rac.configservice.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {
    List<Rule> findAllByOrderByIdDesc();
}
