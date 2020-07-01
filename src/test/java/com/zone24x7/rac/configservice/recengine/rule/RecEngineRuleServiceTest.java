package com.zone24x7.rac.configservice.recengine.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.rule.RuleDetail;
import com.zone24x7.rac.configservice.rule.RuleList;
import com.zone24x7.rac.configservice.rule.RuleService;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.RULES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecEngineRuleServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineRuleService recEngineRuleService;

    @Mock
    private RuleService ruleService;

    @Mock
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("get rule config method")
    class GetRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRuleConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(RULES)).thenReturn(recEngine);

            // Actual
            String actual = recEngineRuleService.getRuleConfig();

            // Assert
            assertEquals(expected, actual);
        }

    }




    @Nested
    @DisplayName("add rule config method")
    class AddRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddRuleConfigForCorrectValues() {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");

            // Set mock rec engine.
            RecEngine recEngine = mock(RecEngine.class);

            // Setup repository method findByConfigType() return value.
            when(recEngineRepository.findByConfigType(RULES)).thenReturn(recEngine);

            // Save
            recEngineRepository.save(recEngine);

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(recEngine);

            // Actual
            CSResponse actual = recEngineRuleService.addRuleConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }




    @Nested
    @DisplayName("update rule config method")
    class UpdateRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRuleConfigForCorrectValues() throws Exception {

            // Mock
            List<BaseExpr> baseExprs = new ArrayList<>();
            baseExprs.add(new BaseExpr());
            RuleDetail ruleDetail = new RuleDetail();
            ruleDetail.setId(1);
            ruleDetail.setName("test rule");
            ruleDetail.setType("BOOST");
            ruleDetail.setIsGlobal(false);
            ruleDetail.setMatchingCondition("");
            ruleDetail.setMatchingConditionJson(baseExprs);
            ruleDetail.setActionCondition("");
            ruleDetail.setActionConditionJson(baseExprs);
            List<RuleDetail> ruleDetails = new ArrayList<>();
            ruleDetails.add(ruleDetail);
            when(ruleService.getAllRules()).thenReturn(new RuleList(ruleDetails));


            // Save
            recEngineRuleService.updateRuleConfig();

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(any());


            // Assert rec engine rule.
            RecEngineRule recEngineRule = new RecEngineRule();
            recEngineRule.setId(1);
            recEngineRule.setName("test");
            recEngineRule.setType("type");
            recEngineRule.setIsGlobal(false);
            recEngineRule.setMatchingCondition("");
            recEngineRule.setActionCondition("");

            assertEquals(1, recEngineRule.getId());
            assertEquals("test", recEngineRule.getName());
            assertEquals("type", recEngineRule.getType());
            assertFalse(recEngineRule.getIsGlobal());
            assertEquals("", recEngineRule.getMatchingCondition());
            assertEquals("", recEngineRule.getActionCondition());



            // Assert rec engine rule list.
            List<RecEngineRule> recEngineRules = new ArrayList<>();
            recEngineRules.add(recEngineRule);
            RecEngineRuleList recEngineRuleList = new RecEngineRuleList();
            recEngineRuleList.setRules(recEngineRules);

            assertEquals(recEngineRules, recEngineRuleList.getRules());

        }




        @Test
        @DisplayName("test for JsonProcessingException")
        void testUpdateRuleConfigForJsonProcessingException() throws JsonProcessingException {

            // Mock
            List<BaseExpr> baseExprs = new ArrayList<>();
            baseExprs.add(new BaseExpr());
            RuleDetail ruleDetail = new RuleDetail();
            ruleDetail.setId(1);
            ruleDetail.setName("test rule");
            ruleDetail.setType("BOOST");
            ruleDetail.setIsGlobal(false);
            ruleDetail.setMatchingCondition("");
            ruleDetail.setMatchingConditionJson(baseExprs);
            ruleDetail.setActionCondition("");
            ruleDetail.setActionConditionJson(baseExprs);
            List<RuleDetail> ruleDetails = new ArrayList<>();
            ruleDetails.add(ruleDetail);
            when(ruleService.getAllRules()).thenReturn(new RuleList(ruleDetails));

            JsonProcessingException jpe = mock(JsonProcessingException.class);
            when(objectMapper.writeValueAsString(any())).thenThrow(jpe);

            // Exception
            Exception exception = assertThrows(ServerException.class, () -> recEngineRuleService.updateRuleConfig());

            // Assert
            assertEquals(Strings.REC_ENGINE_RULE_CONFIG_UPDATE_FAILED, exception.getMessage());

        }


    }


}