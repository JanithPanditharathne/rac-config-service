package com.zone24x7.rac.configservice.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;

    @GetMapping("/rules")
    public RuleList getAllRules() {
        return new RuleList(new ArrayList<>());
    }
}
