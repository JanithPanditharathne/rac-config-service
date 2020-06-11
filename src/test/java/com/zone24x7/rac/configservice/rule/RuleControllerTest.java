package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(value = RuleController.class)
public class RuleControllerTest {

    @MockBean
    private RuleService ruleService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Unit test for get all rules.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllRules() throws Exception {

        // Mock
        RuleList ruleList = new RuleList(new ArrayList<>());
        Mockito.when(ruleService.getAllRules()).thenReturn(ruleList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(ruleList);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rules")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for get rule by ID.
     *
     * @throws Exception Exceptions to throw
     */
    @Test
    void getRule() throws Exception {

        RuleDetail ruleDetail = new RuleDetail();
        Mockito.when(ruleService.getRule(anyInt())).thenReturn(ruleDetail);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(ruleDetail);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rules/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for add new rule.
     *
     * @throws Exception Exceptions to throw
     */
    @Test
    void addRule() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, RULE_ADDED_SUCCESSFULLY);
        Mockito.when(ruleService.addRule(any())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        String ruleJson = "{\"name\":\"Test Rule 7\",\"type\":\"BURY\",\"isGlobal\":false,\"matchingConditionJson\":[{" +
                "\"type\":\"Brand\",\"condition\":\"AND\",\"value\":[{\"id\":6,\"name\":\"1 by 8\"},{\"id\":4983,\"name" +
                "\":\"100 Facets of Love\"},{\"id\":2,\"name\":\"1010\"}]},{\"type\":\"ProductNumber\",\"condition\":" +
                "\"AND\",\"value\":[\"244\",\"4523\"]}],\"actionConditionJson\":[{\"type\":\"Price\",\"condition\":\"AND" +
                "\",\"value\":{\"operator\":\"eq\",\"price\":53}}]}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ruleJson);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
