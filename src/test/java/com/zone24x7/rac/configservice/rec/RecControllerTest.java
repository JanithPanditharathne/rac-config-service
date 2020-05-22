package com.zone24x7.rac.configservice.rec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.DisplayName;
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

import static com.zone24x7.rac.configservice.util.Strings.REC_ADD_SUCCESS;
import static com.zone24x7.rac.configservice.util.Strings.REC_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
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
    void getAllRecs() throws Exception {

        // Mock
        RecDetailList recDetailList = new RecDetailList();
        recDetailList.setRecs(new ArrayList<>());
        Mockito.when(recService.getAllRecs()).thenReturn(recDetailList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(recDetailList);

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
    void getRec() throws Exception {

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
    void addRec() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS, REC_ADD_SUCCESS);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock
        RecDetail recDetail = new RecDetail();
        Mockito.when(recService.addRec(any())).thenReturn(csResponse);

        String recJson = "{\"name\":\"Test Rec Name 1\",\"bundleId\":32}";

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
    }



    @Test
    @DisplayName("edit rec method")
    void editRec() throws Exception {

        // Expected
        CSResponse csResponse = new CSResponse(SUCCESS, REC_UPDATED_SUCCESSFULLY);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Mock
        RecDetail recDetail = new RecDetail();
        Mockito.when(recService.editRec(anyInt(), any())).thenReturn(csResponse);

        String recJson = "{\"name\":\"Test Rec Name 1\",\"bundleId\":32}";

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
}
