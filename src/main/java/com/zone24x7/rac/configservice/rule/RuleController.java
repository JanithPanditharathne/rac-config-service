package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Add rule.
     *
     * @param ruleDetail Rule details
     * @return           CS Response
     * @throws ValidationException     Validation exception to throw
     * @throws JsonProcessingException Parsing exception to throw
     * @throws ServerException         Server exception to throw
     */
    @PostMapping("/rules")
    public CSResponse addRule(@RequestBody RuleDetail ruleDetail) throws ValidationException, JsonProcessingException, ServerException {
        return ruleService.addRule(ruleDetail);
    }
}
