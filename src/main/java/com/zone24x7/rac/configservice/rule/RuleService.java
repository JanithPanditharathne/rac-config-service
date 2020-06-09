package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.RULE_ID_INVALID;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);

    /**
     * Get all rules.
     *
     * @return List of rules
     */
    RuleList getAllRules() {

        List<Rule> rulesList = ruleRepository.findAll();

        List<RuleDetail> ruleDetailList = new ArrayList<>();
        rulesList.forEach(rule -> {

            RuleDetail ruleDetail = getRuleDTO(rule);
            ruleDetailList.add(ruleDetail);
        });

        return new RuleList(ruleDetailList);
    }

    /**
     * Get rule by ID.
     *
     * @param id Rule ID
     * @return   Rule DTO
     * @throws ValidationException Exception to throw
     */
    RuleDetail getRule(int id) throws ValidationException {

        // Retrieve rule by ID.
        Optional<Rule> optionalRule = ruleRepository.findById(id);

        if (!optionalRule.isPresent()) {
            throw new ValidationException(RULE_ID_INVALID);
        }

        // Fill rule details and return.
        return getRuleDTO(optionalRule.get());
    }

    /**
     * Method to fill rule DTO from rule entity.
     *
     * @param rule Rule entity
     * @return     Rule DTO
     */
    private RuleDetail getRuleDTO(Rule rule) {
        try {
            BaseExpr matchingCondition = objectMapper.readValue(rule.getMatchingConditionJson(), BaseExpr.class);
            List<BaseExpr> matchingConditionList = Arrays.asList(matchingCondition);

            BaseExpr actionCondition = objectMapper.readValue(rule.getActionConditionJson(), BaseExpr.class);
            List<BaseExpr> actionConditionList = Arrays.asList(actionCondition);

            return new RuleDetail(rule.getId(), rule.getName(), rule.getType(), rule.getIsGlobal(), rule.getMatchingCondition(),
                                  matchingConditionList, rule.getActionCondition(), actionConditionList);

        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to parse expression ", e);
        }

        return null;
    }
}


