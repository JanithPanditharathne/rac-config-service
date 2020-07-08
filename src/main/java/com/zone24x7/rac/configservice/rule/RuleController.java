package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zone24x7.rac.configservice.actiontrace.ActionTraceService;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ActionTraceService actionTraceService;

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
        actionTraceService.logRequestDetails(ruleDetail);
        CSResponse csResponse = ruleService.addRule(ruleDetail);
        actionTraceService.add(ruleDetail);
        return csResponse;
    }

    /**
     * Edit rule.
     *
     * @param id         Rule ID
     * @param ruleDetail Rule details
     * @return           CS Response
     * @throws ValidationException     Validation exception to throw
     * @throws JsonProcessingException Parsing exception to throw
     * @throws ServerException         Server exception to throw
     */
    @PutMapping("/rules/{id}")
    public CSResponse editRule(@PathVariable int id, @RequestBody RuleDetail ruleDetail) throws ValidationException, JsonProcessingException, ServerException {
        actionTraceService.logRequestDetails(ruleDetail);
        CSResponse csResponse = ruleService.editRule(id, ruleDetail);
        actionTraceService.add(ruleDetail);
        return csResponse;
    }


    /**
     * Delete rule.
     *
     * @param id rule id to delete.
     * @return status response.
     * @throws ValidationException for invalid rule id.
     */
    @DeleteMapping("/rules/{id}")
    public CSResponse deleteRule(@PathVariable int id) throws ValidationException, ServerException {
        CSResponse csResponse = ruleService.deleteRule(id);
        actionTraceService.add();
        return csResponse;
    }
}
