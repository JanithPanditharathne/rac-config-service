package com.zone24x7.rac.configservice.metadata;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(value = MetadataController.class)
public class MetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetadataService metadataService;

    /**
     * Unit test when adding a channel.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void addChannel() throws Exception {

        CSResponse csResponse = new CSResponse(SUCCESS, "CS-6000: Channel name field is missing");
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        Mockito.when(metadataService.addChannel(any())).thenReturn(csResponse);
        String inputJson = "{\"name\":\"Web\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post("/v1/metadata/channels")
                                                      .accept(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)
                                                      .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when retrieving all channels.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllChannels() throws Exception {

        ChannelListDTO channelListDTO = new ChannelListDTO();
        List<MetadataDTO> metadataDTOList = new ArrayList<>();
        channelListDTO.setChannels(metadataDTOList);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(channelListDTO);

        Mockito.when(metadataService.getAllChannels()).thenReturn(channelListDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/v1/metadata/channels")
                                                      .accept(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }
}
