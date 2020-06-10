package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
