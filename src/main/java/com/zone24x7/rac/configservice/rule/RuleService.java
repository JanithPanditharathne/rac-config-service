package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.RULE_ID_INVALID;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;


    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    /**
     * Get all rules.
     *
     * @return List of rules
     */
    RuleList getAllRules() {

        List<RuleDetail> ruleDetailList = new ArrayList<>();
        List<Rule> rules = ruleRepository.findAll();
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
    RuleDetail getRule(int id) throws ValidationException {

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
}


