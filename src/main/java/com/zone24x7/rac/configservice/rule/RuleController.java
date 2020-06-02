package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleController.class);

    @GetMapping("/rules")
    public RuleList getAllRules() {
        List<RuleDetail> rules = new ArrayList<>();

        List<Rule> allRules = ruleRepository.findAll();
        allRules.forEach(rule -> {

            try {
                RuleDetail ruleDetail = new RuleDetail();
                ruleDetail.setId(rule.getId());
                ruleDetail.setName(rule.getName());
                ruleDetail.setType(rule.getType());
                ruleDetail.setIsGlobal(rule.isGlobal());

                ObjectMapper objectMapper = new ObjectMapper();
                BaseRuleExpression matchingCondition = objectMapper.readValue(rule.getMatchingConditionJson(), BaseRuleExpression.class);
                List<BaseRuleExpression> matchingConditionList = Arrays.asList(matchingCondition);
                ruleDetail.setMatchingConditionJson(matchingConditionList);

                BaseRuleExpression actionCondition = objectMapper.readValue(rule.getActionConditionJson(), BaseRuleExpression.class);
                List<BaseRuleExpression> actionConditionList = Arrays.asList(actionCondition);
                ruleDetail.setActionConditionJson(actionConditionList);

                rules.add(ruleDetail);

            } catch (JsonProcessingException e) {
                LOGGER.info("Parsing exception", e);
            }
        });

        return new RuleList(rules);
    }
}
