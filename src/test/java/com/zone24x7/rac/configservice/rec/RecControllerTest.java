package com.zone24x7.rac.configservice.rec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;
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

@WebMvcTest(value = RecController.class)
public class RecControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecService recService;

    @Test
    @DisplayName("get all recs method")
    void testGetAllRecs() throws Exception {

        // Mock
        RecList recList = new RecList();
        recList.setRecs(new ArrayList<>());
        Mockito.when(recService.getAllRecs()).thenReturn(recList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(recList);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/recs")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("get rec method")
    void testGetRec() throws Exception {

        // Mock
        RecDetail recDetail = new RecDetail();
        Mockito.when(recService.getRec(anyInt())).thenReturn(recDetail);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(recDetail);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/recs/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("add rec method")
    void testAddRec() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS, REC_ADD_SUCCESS);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock
        RecDetail recDetail = new RecDetail();
        Mockito.when(recService.addRec(any())).thenReturn(csResponse);

        String recJson = "{\"name\":\"Test Rec Name 1\",\"bundle\":{\"id\":32}}";

        // Set rec id.
        MDC.put(HEADER_CS_META, "1");

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recs")
                .accept(MediaType.APPLICATION_JSON)
                .content(recJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);

        // Remove rec id.
        MDC.remove(HEADER_CS_META);
    }



    @Test
    @DisplayName("edit rec method")
    void testEditRec() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS, REC_UPDATED_SUCCESSFULLY);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock
        Mockito.when(recService.editRec(anyInt(), any())).thenReturn(csResponse);

        String recJson = "{\"name\":\"Test Rec Name 1\",\"bundle\":{\"id\":32}}";

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/recs/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(recJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("delete rec method")
    void testDeleteRec() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS, REC_DELETED_SUCCESSFULLY);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock
        Mockito.when(recService.deleteRec(anyInt())).thenReturn(csResponse);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/recs/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
