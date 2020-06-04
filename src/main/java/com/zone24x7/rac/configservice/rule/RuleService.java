package com.zone24x7.rac.configservice.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    RuleList getAllRules() {
        List<Rule> rulesList = ruleRepository.findAll();

        return new RuleList(rulesList);
    }
}
