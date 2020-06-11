package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.rule.expression.RuleExprConverter;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    @Lazy
    private RecEngineService recEngineService;


    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    /**
     * Get all rules.
     *
     * @return List of rules
     */
    public RuleList getAllRules() {

        List<RuleDetail> ruleDetailList = new ArrayList<>();
        List<Rule> rules = ruleRepository.findAllByOrderByIdDesc();
        rules.forEach(rule -> ruleDetailList.add(getRuleDetail(rule)));
        return new RuleList(ruleDetailList);
    }

    /**
     * Get rule detail by id.
     *
     * @param id Rule id
     * @return   Rule detail
     * @throws ValidationException Exception to throw
     */
    public RuleDetail getRule(int id) throws ValidationException {

        // Validate rule id.
        RuleValidations.validateID(id);

        // Find the given rule.
        Optional<Rule> optionalRule = ruleRepository.findById(id);
        if (!optionalRule.isPresent()) {
            throw new ValidationException(RULE_ID_INVALID);
        }

        // Return rule details.
        return getRuleDetail(optionalRule.get());
    }

    /**
     * Method to get rule detail from rule entity.
     *
     * @param rule Rule entity
     * @return     Rule detail
     */
    private static RuleDetail getRuleDetail(Rule rule) {
        try {
            return new RuleDetail(rule.getId(), rule.getName(), rule.getType(), rule.getIsGlobal(),
                    rule.getMatchingCondition(), rule.getMatchingConditionJsonAsList(),
                    rule.getActionCondition(), rule.getActionConditionJsonAsList());
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to parse rule expression. ", e);
        }
        return null;
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
    public CSResponse addRule(RuleDetail ruleDetail) throws ValidationException, JsonProcessingException, ServerException {

        // Validate rule details.
        validateRuleDetails(ruleDetail);

        // Get matching condition string.
        String matchingCondition = RuleExprConverter.convertJsonExprToString(ruleDetail.getMatchingConditionJson());

        // Get action condition string.
        String actionCondition = RuleExprConverter.convertJsonExprToString(ruleDetail.getActionConditionJson());

        // Create a new rule.
        Rule rule = new Rule(ruleDetail.getName(), ruleDetail.getType(), ruleDetail.getIsGlobal(), matchingCondition,
                ruleDetail.getMatchingConditionJsonAsString(), actionCondition, ruleDetail.getActionConditionJsonAsString());

        // Save new rule.
        ruleRepository.save(rule);

        // Update rec engine rule config.
        recEngineService.updateRuleConfig();

        // Return status.
        return new CSResponse(SUCCESS, RULE_ADDED_SUCCESSFULLY);
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
    public CSResponse editRule(int id, RuleDetail ruleDetail) throws ValidationException, JsonProcessingException, ServerException {

        // Validate rule ID.
        RuleValidations.validateID(id);

        Optional<Rule> optionalRule = ruleRepository.findById(id);
        if (!optionalRule.isPresent()) {
            throw new ValidationException(RULE_ID_INVALID);
        }

        // Validate rule details.
        validateRuleDetails(ruleDetail);

        // Get matching condition string.
        String matchingCondition = RuleExprConverter.convertJsonExprToString(ruleDetail.getMatchingConditionJson());

        // Get action condition string.
        String actionCondition = RuleExprConverter.convertJsonExprToString(ruleDetail.getActionConditionJson());

        // Create a new rule.
        Rule rule = new Rule(ruleDetail.getName(), ruleDetail.getType(), ruleDetail.getIsGlobal(), matchingCondition,
                             ruleDetail.getMatchingConditionJsonAsString(), actionCondition, ruleDetail.getActionConditionJsonAsString());
        rule.setId(id);

        // Save new rule.
        ruleRepository.save(rule);

        // Update rec engine rule config.
        recEngineService.updateRuleConfig();

        // Return status.
        return new CSResponse(SUCCESS, RULE_UPDATED_SUCCESSFULLY);
    }

    /**
     * Validate rule details.
     *
     * @param ruleDetail Rule details
     * @throws ValidationException if validation fail.
     */
    private static void validateRuleDetails(RuleDetail ruleDetail) throws ValidationException {

        // Validate rule name.
        RuleValidations.validateName(ruleDetail.getName());

        // Validate rule type.
        RuleValidations.validateType(ruleDetail.getType());

        // Validate matching condition json.
        RuleValidations.validateMatchingConditionJson(ruleDetail.getMatchingConditionJson());

        // Validate action condition json.
        RuleValidations.validateActionConditionJson(ruleDetail.getActionConditionJson());

    }
}


