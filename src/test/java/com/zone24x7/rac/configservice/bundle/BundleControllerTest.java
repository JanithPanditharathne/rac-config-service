package com.zone24x7.rac.configservice.bundle;

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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = BundleController.class)
public class BundleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BundleService bundleService;

    /**
     * Unit test when retrieving all bundles.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllBundles() throws Exception {

        // Mock
        List<BundleSummaryDTO> bundleSummaryDTOList = new ArrayList<>();
        bundleSummaryDTOList.add(new BundleSummaryDTO(1, "Bundle 1"));
        bundleSummaryDTOList.add(new BundleSummaryDTO(2, "Bundle 2"));

        BundleSummaryListDTO bundleSummaryListDTO = new BundleSummaryListDTO(bundleSummaryDTOList);
        Mockito.when(bundleService.getAllBundles()).thenReturn(bundleSummaryListDTO);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(bundleSummaryListDTO);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/bundles")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when retrieving bundle details.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getBundleDetails() throws Exception {

        // Expected
        String expected = "{\"id\":\"1\",\"name\":\"Bundle 1\",\"defaultLimit\":5,\"combineEnabled\":false," +
                "\"combineDisplayText\":\"Test\",\"algorithms\":[{\"id\":100,\"name\":\"Top Trending\",\"rank\":0," +
                "\"defaultDisplayText\":\"Top Trending\",\"customDisplayText\":\"Top Trending Products\"}]}";

        // Mock
        BundleAlgorithmDTO bundleAlgorithmDTO = new BundleAlgorithmDTO(100, "Top Trending", 0,
                                                                       "Top Trending",
                                                                       "Top Trending Products");

        BundleDetailDTO bundleDetailDTO = new BundleDetailDTO("1", "Bundle 1", 5,
                                                              false, "Test",
                                                              Arrays.asList(bundleAlgorithmDTO));
        Mockito.when(bundleService.getBundle(100)).thenReturn(bundleDetailDTO);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/bundles/100")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
