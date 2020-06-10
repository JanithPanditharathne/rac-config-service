package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RuleServiceTest {

    @InjectMocks
    private RuleService ruleService;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private ObjectMapper objectMapper;

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
                                 "[{\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":6,\"name\":\"1 by 8\"}]}]",
                                 "(brand == \"Nike\")",
                                 "[{\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":1,\"name\":\"Nike\"}]}]");
            rule.setId(1);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            // Expected
            String expected = "{\"id\":1,\"name\":\"Rule 1\",\"type\":\"BOOST\",\"isGlobal\":true,\"matchingCondition\"" +
                    ":\"(brand == \\\"1 by 8\\\")\",\"matchingConditionJson\":[{\"type\":\"Brand\",\"condition\":\"AND\"" +
                    ",\"value\":[{\"id\":6,\"name\":\"1 by 8\"}]}],\"actionCondition\":\"(brand == \\\"Nike\\\")\"," +
                    "\"actionConditionJson\":[{\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":1,\"name\":" +
                    "\"Nike\"}]}]}";

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
}
