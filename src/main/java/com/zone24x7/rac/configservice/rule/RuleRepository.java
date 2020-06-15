package com.zone24x7.rac.configservice.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {

    /**
     * Find all rules ordered by ID in descending order.
     *
     * @return List of rules
     */
    List<Rule> findAllByOrderByIdDesc();

    /**
     * Find all global rules.
     *
     * @param isGlobal Global index
     * @return         List of rules
     */
    List<Rule> findAllByIsGlobal(Boolean isGlobal);
}
