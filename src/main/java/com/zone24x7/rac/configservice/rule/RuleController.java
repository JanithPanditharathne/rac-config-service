package com.zone24x7.rac.configservice.rule;

import com.zone24x7.rac.configservice.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    /**
     * Get all rules.
     *
     * @return Rule details
     */
    @GetMapping("/rules")
    public RuleList getAllRules() {
        return ruleService.getAllRules();
    }

    /**
     * Get rule detail by id.
     *
     * @param id Rule id
     * @return   Rule detail.
     * @throws ValidationException Exception to throw
     */
    @GetMapping("/rules/{id}")
    public RuleDetail getRule(@PathVariable int id) throws ValidationException {
        return ruleService.getRule(id);
    }
}
