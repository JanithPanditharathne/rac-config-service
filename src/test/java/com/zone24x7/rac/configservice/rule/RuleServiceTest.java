package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import com.zone24x7.rac.configservice.rule.expression.brand.BrandExpr;
import com.zone24x7.rac.configservice.rule.expression.price.PriceExpr;
import com.zone24x7.rac.configservice.rule.expression.price.PriceValue;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RuleServiceTest {

    @InjectMocks
    private RuleService ruleService;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RecEngineService recEngineService;

    @Test
    @DisplayName("get all rules method")
    void testGetAllRules() throws JsonProcessingException {

        // Expected
        List<RuleDetail> ruleDetails = new ArrayList<>();
        ruleDetails.add(new RuleDetail());
        RuleList expected = new RuleList(ruleDetails);

        // Mock
        List<Rule> rules = new ArrayList<>();
        Rule rule = new Rule("Rule 1", "BOOST", true, "(brand == \"1 by 8\")",
                             "{\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":6,\"name\":\"1 by 8\"}]}",
                             "(brand == \"Nike\")",
                             "{\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":1,\"name\":\"Nike\"}]}");
        rule.setId(1);
        rules.add(rule);
        when(ruleRepository.findAllByOrderByIdDesc()).thenReturn(rules);

        BaseExpr baseExpr = mock(BaseExpr.class);
        when(objectMapper.readValue(anyString(), eq(BaseExpr.class))).thenReturn(baseExpr);

        // Actual
        RuleList actual = ruleService.getAllRules();

        // Assert
        assertEquals(expected.getRules().size(), actual.getRules().size());
    }

    @Nested
    @DisplayName("get rule method")
    class GetRule {

        @Test
        @DisplayName("test for negative rule id")
        void testGetRuleForNegativeRuleID() {

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    // Get bundle.
                    ruleService.getRule(-1));

            String actual = validationException.getMessage();

            // Assert
            assertEquals(Strings.RULE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for invalid rule id")
        void testGetRuleForInvalidRuleID() {

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    // Get bundle.
                    ruleService.getRule(1));

            String actual = validationException.getMessage();

            // Assert
            assertEquals(Strings.RULE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for valid rule id")
        void testGetRuleForValidRuleID() throws ValidationException, JsonProcessingException {

            // Mock
            Rule rule = new Rule("Rule 1", "BOOST", true, "(brand == \"1 by 8\")",
                                 "[{\"type\":\"Brand\",\"condition\":\"AND\",\"operator\":\"eq\",\"value\":[\"1 by 8\"]}]",
                                 "(brand == \"Nike\")",
                                 "[{\"type\":\"Brand\",\"condition\":\"AND\",\"operator\":\"eq\",\"value\":[\"Nike\"]}]");
            rule.setId(1);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            // Expected
            String expected = "{\"id\":1,\"name\":\"Rule 1\",\"type\":\"BOOST\",\"isGlobal\":true,\"matchingCondition\"" +
                    ":\"(brand == \\\"1 by 8\\\")\",\"matchingConditionJson\":[{\"type\":\"Brand\",\"condition\":\"AND\"" +
                    ",\"operator\":\"eq\",\"value\":[\"1 by 8\"]}],\"actionCondition\":\"(brand == \\\"Nike\\\")\"," +
                    "\"actionConditionJson\":[{\"type\":\"Brand\",\"condition\":\"AND\",\"operator\":\"eq\",\"value\":[" +
                    "\"Nike\"]}]}";

            // Actual
            RuleDetail actualRuleDetail = ruleService.getRule(rule.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            String actual = objectMapper.writeValueAsString(actualRuleDetail);

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for unable to parse rule expression")
        void testGetRuleForParsingException() throws ValidationException {

            // Mock
            Rule rule = new Rule("Rule 1", "BOOST", true, "(brand == \"1 by 8\")",
                                 "", "(brand == \"Nike\")", "");
            rule.setId(1);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            // Get bundle.
            RuleDetail actualRuleDetail = ruleService.getRule(1);

            // Assert
            assertNull(actualRuleDetail);
        }
    }

    @Nested
    @DisplayName("add rule method")
    class AddRule {

        @Test
        @DisplayName("test for missing rule name")
        void testAddRuleForMissingRuleName() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail())
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_NAME_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty rule name")
        void testAddRuleForEmptyRuleName() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "", "", false, null,
                                                       null, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_NAME_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for missing rule type")
        void testAddRuleForMissingRuleType() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", null, false, null,
                                                       null, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_TYPE_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty rule type")
        void testAddRuleForEmptyRuleType() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", "", false, null,
                                                       null, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_TYPE_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for invalid rule type")
        void testAddRuleForInvalidRuleType() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", "abc", false, null,
                                                       null, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_TYPE_INVALID, actual);
        }

        @Test
        @DisplayName("test for missing rule matching condition json")
        void testAddRuleForMissingRuleMatchingConditionJson() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", "BOOST", false, null,
                                                       null, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_MATCHING_CONDITION_JSON_CANNOT_BE_NULL, actual);
        }


        @Test
        @DisplayName("test for missing rule action condition json")
        void testAddRuleForMissingRuleActionConditionJson() {

            List<BaseExpr> matchingConditionJson = new ArrayList<>();
            BaseExpr baseExpr = new BaseExpr();
            baseExpr.setType("Brand");
            baseExpr.setType("AND");
            matchingConditionJson.add(baseExpr);

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", "BOOST", false, null,
                                                       matchingConditionJson, null, null))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_ACTION_CONDITION_JSON_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty rule action condition json")
        void testAddRuleForEmptyRuleActionConditionJson() {

            List<BaseExpr> matchingConditionJson = new ArrayList<>();
            BaseExpr baseExpr = new BaseExpr();
            baseExpr.setType("Brand");
            baseExpr.setType("AND");
            matchingConditionJson.add(baseExpr);

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.addRule(new RuleDetail(0, "Rule 1", "BOOST", false, null,
                                                       matchingConditionJson, null, new ArrayList<>()))
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_ACTION_CONDITION_JSON_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testAddRuleForCorrectValues() throws JsonProcessingException, ServerException, ValidationException {

            // Expected.
            CSResponse expected = new CSResponse(SUCCESS, RULE_ADDED_SUCCESSFULLY);

            // Matching condition json.
            BrandExpr matching = new BrandExpr();
            matching.setType("Brand");
            matching.setCondition("AND");
            matching.setValue(Collections.singletonList("Nike"));

            List<BaseExpr> matchingConditionJson = new ArrayList<>();
            matchingConditionJson.add(matching);

            // Action condition json.
            PriceValue priceValue = new PriceValue(34.0);

            PriceExpr action = new PriceExpr();
            action.setType("Price");
            action.setCondition("OR");
            action.setValue(priceValue);

            List<BaseExpr> actionConditionJson = new ArrayList<>();
            actionConditionJson.add(action);

            // Actual
            CSResponse actual = ruleService.addRule(new RuleDetail(0, "Rule 1", "BOOST", false,
                                                                       null, matchingConditionJson,
                                                                       null, actionConditionJson));

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(ruleRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateRuleConfig();
        }
    }

    @Nested
    @DisplayName("edit rule method")
    class EditRule {

        @Test
        @DisplayName("test for negative rule id")
        void testEditRuleForNegativeRuleID() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.editRule(-1, new RuleDetail())
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for invalid rule id")
        void testEditRuleForInvalidRuleID() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    ruleService.editRule(1, new RuleDetail())
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(RULE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testEditRuleForCorrectValues() throws JsonProcessingException, ServerException, ValidationException {

            // Expected.
            CSResponse expected = new CSResponse(SUCCESS, RULE_UPDATED_SUCCESSFULLY);

            // Mock
            Rule rule = mock(Rule.class);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            // Matching condition json.
            BrandExpr matching = new BrandExpr();
            matching.setType("Brand");
            matching.setCondition("AND");
            matching.setValue(Collections.singletonList("Nike"));

            List<BaseExpr> matchingConditionJson = new ArrayList<>();
            matchingConditionJson.add(matching);

            // Action condition json.
            PriceValue priceValue = new PriceValue(34.0);

            PriceExpr action = new PriceExpr();
            action.setType("Price");
            action.setCondition("OR");
            action.setValue(priceValue);

            List<BaseExpr> actionConditionJson = new ArrayList<>();
            actionConditionJson.add(action);

            // Actual
            CSResponse actual = ruleService.editRule(1, new RuleDetail(0, "Rule 1", "BOOST",
                                                                       false, null,
                                                                       matchingConditionJson, null,
                                                                       actionConditionJson));

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(ruleRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateRuleConfig();
        }
    }
}
