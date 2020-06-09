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
    void getBundleConfig() throws Exception {

        // Expected
        String expected = "{\"bundles\":[{\"id\":\"1\",\"name\":\"Bundle 1\",\"type\":null,\"defaultLimit\":5," +
                "\"algorithms\":[{\"rank\":0,\"algorithm\":{\"id\":\"100\",\"name\":\"TopTrending\"," +
                "\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Top Trending\",\"customDisplayText\":\"Top Trending " +
                "Products\"}},{\"rank\":1,\"algorithm\":{\"id\":\"101\",\"name\":\"BestSellers\"," +
                "\"type\":\"FLAT_ALGO\",\"defaultDisplayText\":\"Best Sellers\",\"customDisplayText\":null}}]," +
                "\"algoCombineInfo\":{\"enableCombine\":false,\"combineDisplayText\":null}}]}";

        // Mock service call.
        Mockito.when(recEngineService.getBundleConfig()).thenReturn(expected);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rec-engine/bundles")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);

    }


    @Test
    void getRuleConfig() throws Exception {

        // Expected
        String expected = "{\"rules\":[{\"id\":\"133\",\"name\":\"Test Rule 1\",\"price\":\"BOOST\"," +
                "\"isGlobal\":false,\"matchingCondition\":\"(department == \\\"Shoes\\\" || (department == " +
                "\\\"Clothing\\\" && price == \\\"Tommy\\\"))\",\"actionCondition\":\"(price == \\\"Nike\\\")\"}]}";

        // Mock service call.
        Mockito.when(recEngineService.getRuleConfig()).thenReturn(expected);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rec-engine/rules")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }


    @Test
    void getRecConfig() throws Exception {

        // Expected
        String expected = "{\"recs\":[{\"id\":\"100\",\"name\":\"Sample Rec Config 1\",\"price\":\"REGULAR\"," +
                "\"matchingCondition\":null,\"regularConfig\":{\"bundleId\":\"1201\"},\"testConfig\":null}]}";

        // Mock service call.
        Mockito.when(recEngineService.getRecsConfig()).thenReturn(expected);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rec-engine/recs")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);

    }


    @Test
    void getRecSlotConfig() throws Exception {

        // Expected
        String expected = "{\"recSlots\":[{\"channel\":\"TCom\",\"page\":\"PDP\",\"placeholder\":\"Grid\"," +
                "\"ruleIds\":[\"700\",\"701\"],\"recIds\":[\"100\"]}]}";

        // Mock service call.
        Mockito.when(recEngineService.getRecSlotsConfig()).thenReturn(expected);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/rec-engine/rec-slots")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);

    }










    @Test
    void addBundleConfig() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000:Bundle config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addBundleConfig(Mockito.any())).thenReturn(csResponse);
        String bundleConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/rec-engine/bundles")
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
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000:Rule config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRuleConfig(Mockito.any())).thenReturn(csResponse);
        String ruleConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/rec-engine/rules")
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
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000:Rec config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRecConfig(Mockito.any())).thenReturn(csResponse);
        String recConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/rec-engine/recs")
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
        CSResponse csResponse = new CSResponse(SUCCESS,"CS-0000:Rec slot config json successfully added");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock service call
        Mockito.when(recEngineService.addRecSlotConfig(Mockito.any())).thenReturn(csResponse);
        String recSlotConfig = "{}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/rec-engine/rec-slots")
                .accept(MediaType.APPLICATION_JSON)
                .content(recSlotConfig)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}