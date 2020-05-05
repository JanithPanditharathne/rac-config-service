package com.zone24x7.rac.configservice.recengine;

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
        CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");

        // Mock service call
        Mockito.when(recEngineService.addBundleConfig(Mockito.any())).thenReturn(Mockito.any());
        String algorithmJson = "{\"id\":100,\"name\":\"Top Trending\",\"description\":\"TT description\"," +
                "\"defaultDisplayText\":\"Top Trending\"}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recEngine/bundles")
                .accept(MediaType.APPLICATION_JSON)
                .content(algorithmJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void addRuleConfig() {
    }

    @Test
    void addRecConfig() {
    }

    @Test
    void addRecSlotConfig() {
    }
}