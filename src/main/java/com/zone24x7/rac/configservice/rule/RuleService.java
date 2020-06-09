package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<RuleDTO> ruleDTOList = new ArrayList<>();
        rulesList.forEach(rule -> {

            try {
                String matchingConditionJson = rule.getMatchingConditionJson();
                BaseExpr matchingCondition = objectMapper.readValue(matchingConditionJson, BaseExpr.class);
                List<BaseExpr> matchingConditionList = Arrays.asList(matchingCondition);

                BaseExpr actionCondition = objectMapper.readValue(rule.getActionConditionJson(), BaseExpr.class);
                List<BaseExpr> actionConditionList = Arrays.asList(actionCondition);

                RuleDTO ruleDTO = new RuleDTO(rule.getId(), rule.getName(), rule.getType(), rule.getIsGlobal(),
                                              rule.getMatchingCondition().replace("\"\"", "\"\\"),
                                              matchingConditionList,
                                              rule.getActionCondition().replace("\"\"", "\"\\"),
                                              actionConditionList);

                ruleDTOList.add(ruleDTO);

            } catch (JsonProcessingException e) {
                LOGGER.error("Unable to parse expression ", e);
            }
        });

        return new RuleList(ruleDTOList);
    }
}
