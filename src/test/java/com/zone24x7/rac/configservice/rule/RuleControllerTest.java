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

import static com.zone24x7.rac.configservice.util.Strings.RULE_ADDED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.RULE_DELETED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.RULE_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
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

        String ruleJson = "{\n" +
                "  \"name\": \"Test Rule 1 \",\n" +
                "  \"type\": \"BOOST\",\n" +
                "  \"isGlobal\": false,\n" +
                "  \"matchingCondition\": \"\",\n" +
                "  \"matchingConditionJson\": [\n" +
                "    {\n" +
                "      \"type\": \"Brand\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": [\n" +
                "        \"Nike\",\n" +
                "        \"Puma\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"ProductNumber\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": [\n" +
                "        \"244\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"actionCondition\": \"\",\n" +
                "  \"actionConditionJson\": [\n" +
                "    {\n" +
                "      \"type\": \"Price\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": {\n" +
                "        \"price\": 53.0\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

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

    /**
     * Unit test for edit rule.
     *
     * @throws Exception Exceptions to throw
     */
    @Test
    void editRule() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, RULE_UPDATED_SUCCESSFULLY);
        Mockito.when(ruleService.editRule(anyInt(), any())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        String ruleJson = "{\n" +
                "  \"name\": \"Test Rule 1 \",\n" +
                "  \"type\": \"BOOST\",\n" +
                "  \"isGlobal\": false,\n" +
                "  \"matchingCondition\": \"\",\n" +
                "  \"matchingConditionJson\": [\n" +
                "    {\n" +
                "      \"type\": \"Brand\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": [\n" +
                "        \"Nike\",\n" +
                "        \"Puma\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"ProductNumber\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": [\n" +
                "        \"244\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"actionCondition\": \"\",\n" +
                "  \"actionConditionJson\": [\n" +
                "    {\n" +
                "      \"type\": \"Price\",\n" +
                "      \"condition\": \"AND\",\n" +
                "      \"operator\": \"eq\",\n" +
                "      \"value\": {\n" +
                "        \"price\": 53.0\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/rules/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ruleJson);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }




    /**
     * Unit test for delete rule.
     *
     * @throws Exception Exceptions to throw
     */
    @Test
    void deleteRule() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, RULE_DELETED_SUCCESSFULLY);
        Mockito.when(ruleService.deleteRule(anyInt())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/rules/1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
