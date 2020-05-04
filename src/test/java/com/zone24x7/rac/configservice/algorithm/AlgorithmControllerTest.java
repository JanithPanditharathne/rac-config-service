package com.zone24x7.rac.configservice.algorithm;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(value = AlgorithmController.class)
class AlgorithmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlgorithmService algorithmService;


    @Test
    void getAllAlgorithms() throws Exception {

        // Expected
        String expected = "{\"algorithms\":[{\"id\":100,\"name\":\"Top Trending\",\"description\":\"TT description\"," +
                "\"defaultDisplayText\":\"Top Trending\"},{\"id\":101,\"name\":\"View View\",\"description\":\"VV " +
                "description\",\"defaultDisplayText\":\"View View\"}]}";

        // Mock service call.
        List<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new Algorithm(100, "Top Trending", "TT description", "Top Trending"));
        algorithms.add(new Algorithm(101, "View View", "VV description", "View View"));
        AlgorithmList algorithmList = new AlgorithmList(algorithms);

        Mockito.when(algorithmService.getAllAlgorithms()).thenReturn(algorithmList);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/algorithms")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAlgorithm() throws Exception {

        // Expected
        String expected = "{\"id\":100,\"name\":\"Top Trending\",\"description\":\"TT description\"," +
                "\"defaultDisplayText\":\"Top Trending\"}";

        // Mock service call.
        Algorithm algorithm = new Algorithm(100, "Top Trending", "TT description", "Top Trending");
        Mockito.when(algorithmService.getAlgorithm(100)).thenReturn(algorithm);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/algorithms/100")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void addAlgorithm() throws Exception {

        // Expected
        String expected = "algorithm added!";

        // Mock service call
        Mockito.when(algorithmService.addAlgorithm(Mockito.any())).thenReturn(Mockito.any());
        String algorithmJson = "{\"id\":100,\"name\":\"Top Trending\",\"description\":\"TT description\"," +
                "\"defaultDisplayText\":\"Top Trending\"}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/algorithms")
                .accept(MediaType.APPLICATION_JSON)
                .content(algorithmJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);

    }

    @Test
    void updateAlgorithm() throws Exception {

        // Expected
        String expected = "algorithm updated!";

        // Mock service call
        Mockito.when(algorithmService.updateAlgorithm(Mockito.any(), Mockito.anyInt())).thenReturn(Mockito.any());
        String algorithmJson = "{\"id\":100,\"name\":\"Top Trending\",\"description\":\"TT description\"," +
                "\"defaultDisplayText\":\"Top Trending\"}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/algorithms/100")
                .accept(MediaType.APPLICATION_JSON)
                .content(algorithmJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteAlgorithm() throws Exception {

        // Expected
        String expected = "algorithm deleted!";

        // Mock service call
        Mockito.when(algorithmService.deleteAlgorithm(Mockito.anyInt())).thenReturn("algorithm deleted!");

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/algorithms/100")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}