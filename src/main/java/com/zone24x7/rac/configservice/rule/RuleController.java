package com.zone24x7.rac.configservice.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/rules")
    public RuleList getAllRules() {
        return ruleService.getAllRules();
    }
}
