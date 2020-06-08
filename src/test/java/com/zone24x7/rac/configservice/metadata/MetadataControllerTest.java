package com.zone24x7.rac.configservice.metadata;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(value = MetadataController.class)
class MetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetadataService metadataService;



    /**
     * Unit test when metadata by type.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void testGetMetadata() throws Exception {

        // Mock
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(new Metadata("brands", "Nike"));
        metadata.add(new Metadata("brands", "PUMA"));
        MetadataList metadataList = new MetadataList(metadata);
        Mockito.when(metadataService.getMetadata(anyString())).thenReturn(metadataList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(metadataList).replace("metadata", "brands");

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/metadata/brands")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
