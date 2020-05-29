package com.zone24x7.rac.configservice.recslot;

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

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(value = RecSlotController.class)
public class RecSlotControllerTest {

    @MockBean
    private RecSlotService recSlotService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Unit test for get all rec slots.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllRecSlots() throws Exception {

        // Mock
        RecSlotList recSlotList = new RecSlotList(new ArrayList<>());
        Mockito.when(recSlotService.getAllRecSlots()).thenReturn(recSlotList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(recSlotList);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/recSlots")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for get rec slot details.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getRecSlot() throws Exception {

        // Mock
        RecSlotDetail recSlotDetail = new RecSlotDetail();
        Mockito.when(recSlotService.getRecSlot(anyInt())).thenReturn(recSlotDetail);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(recSlotDetail);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/recSlots/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for add new rec slot.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void addRecSlot() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);
        Mockito.when(recSlotService.addRecSlot(any())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        String recSlotJson = "{\"id\":321,\"channel\":{\"id\":56},\"page\":{\"id\":5},\"placeholder\":{\"id\":2}," +
                "\"rec\":{\"id\":100},\"rules\":[{\"id\":87},{\"id\":90}]}";
        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/recSlots")
                .accept(MediaType.APPLICATION_JSON)
                .content(recSlotJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for edit rec slot.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void editRecSlot() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, REC_SLOT_UPDATED_SUCCESSFULLY);
        Mockito.when(recSlotService.editRecSlot(anyInt(), any())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        String recSlotJson = "{\"id\":321,\"channel\":{\"id\":56},\"page\":{\"id\":5},\"placeholder\":{\"id\":2}," +
                "\"rec\":{\"id\":100},\"rules\":[{\"id\":87},{\"id\":90}]}";
        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/recSlots/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(recSlotJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test for delete rec slot.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void deleteRecSlot() throws Exception {

        // Mock
        CSResponse csResponse = new CSResponse(SUCCESS, REC_DELETED_SUCCESSFULLY);
        Mockito.when(recSlotService.deleteRecSlot(anyInt())).thenReturn(csResponse);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/recSlots/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
