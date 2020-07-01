package com.zone24x7.rac.configservice.recengine.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.rule.RuleList;
import com.zone24x7.rac.configservice.rule.RuleService;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.RULES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecEngineRuleService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int RULE_CONFIG_ID = 2;

    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineRuleService.class);


    /**
     * Get rule config json.
     *
     * @return Rule config json
     */
    public String getRuleConfig() {
        return recEngineRepository.findByConfigType(RULES).getConfigJson();
    }



    /**
     * Update rule config json.
     *
     * @throws ServerException when rule config update failed.
     */
    @Async("ruleTaskExecutor")
    public void updateRuleConfig() throws ServerException {

        // Get all bundles.
        RuleList allRules = ruleService.getAllRules();

        // rule list
        List<RecEngineRule> ruleList = new ArrayList<>();

        // Update rule details for each rule.
        allRules.getRules().forEach(rule ->
                ruleList.add(new RecEngineRule(rule.getId(), rule.getName(), rule.getType(), rule.getIsGlobal(),
                        rule.getMatchingCondition(), rule.getActionCondition())));

        try {

            // Get rule config string.
            RecEngineRuleList recEngineRuleList = new RecEngineRuleList(ruleList);
            String ruleConfigString = objectMapper.writeValueAsString(recEngineRuleList);

            // Update rule config.
            recEngineRepository.save(new RecEngine(RULE_CONFIG_ID, RULES, ruleConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_RULE_CONFIG_UPDATE_FAILED);
        }
    }





    /**
     * Add rule config json.
     *
     * @param ruleConfig Rule config json
     * @return  Response
     */
    public CSResponse addRuleConfig(String ruleConfig) {
        recEngineRepository.save(new RecEngine(RULE_CONFIG_ID, RULES, ruleConfig));
        return new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");
    }



}
