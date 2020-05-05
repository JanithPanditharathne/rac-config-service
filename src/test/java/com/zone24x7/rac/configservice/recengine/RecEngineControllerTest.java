package com.zone24x7.rac.configservice.recengine;

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

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = RecEngineController.class)
class RecEngineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecEngineService recEngineService;


    @Test
    void addBundleConfig() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addBundleConfig(Mockito.any())).thenReturn(csResponse);
        String bundleConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recEngine/bundles")
                .accept(MediaType.APPLICATION_JSON)
                .content(bundleConfig)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void addRuleConfig() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRuleConfig(Mockito.any())).thenReturn(csResponse);
        String ruleConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recEngine/rules")
                .accept(MediaType.APPLICATION_JSON)
                .content(ruleConfig)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void addRecConfig() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000: Rec config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRecConfig(Mockito.any())).thenReturn(csResponse);
        String recConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recEngine/recs")
                .accept(MediaType.APPLICATION_JSON)
                .content(recConfig)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void addRecSlotConfig() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRecSlotConfig(Mockito.any())).thenReturn(csResponse);
        String recSlotConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recEngine/recSlots")
                .accept(MediaType.APPLICATION_JSON)
                .content(recSlotConfig)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}