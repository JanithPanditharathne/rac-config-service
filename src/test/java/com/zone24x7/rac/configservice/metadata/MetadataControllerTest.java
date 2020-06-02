package com.zone24x7.rac.configservice.metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.channel.ChannelList;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.page.PageList;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderList;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(value = MetadataController.class)
class MetadataControllerTest {

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

        CSResponse csResponse = new CSResponse(Strings.SUCCESS, Strings.CHANNEL_ADDED_SUCCESSFULLY);
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

        // Mock
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel(1, "Web"));
        channels.add(new Channel(2, "Mobile"));
        ChannelList channelList = new ChannelList(channels);
        Mockito.when(metadataService.getAllChannels()).thenReturn(channelList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(channelList);

        // Actual
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/metadata/channels")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when adding a page.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void addPage() throws Exception {

        CSResponse csResponse = new CSResponse(Strings.SUCCESS, Strings.PAGE_ADDED_SUCCESSFULLY);
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        Mockito.when(metadataService.addPage(any())).thenReturn(csResponse);
        String inputJson = "{\"name\":\"Home\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post("/v1/metadata/pages")
                                                      .accept(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)
                                                      .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when retrieving all pages.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllPages() throws Exception {

        // Mock
        List<Page> pages = new ArrayList<>();
        pages.add(new Page(1, "Web"));
        pages.add(new Page(2, "Mobile"));
        PageList pageList = new PageList(pages);
        Mockito.when(metadataService.getAllPages()).thenReturn(pageList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(pageList);

        // Actual
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/v1/metadata/pages")
                                                      .accept(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when adding a placeholder.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void addPlaceholder() throws Exception {

        CSResponse csResponse = new CSResponse(Strings.SUCCESS, Strings.PLACEHOLDER_ADDED_SUCCESSFULLY);
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(csResponse);

        Mockito.when(metadataService.addPlaceholder(any())).thenReturn(csResponse);
        String inputJson = "{\"name\":\"Vertical\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post("/v1/metadata/placeholders")
                                                      .accept(MediaType.APPLICATION_JSON)
                                                      .content(inputJson)
                                                      .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when retrieving all placeholders.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getAllPlaceholders() throws Exception {

        // Mock
        List<Placeholder> placeholders = new ArrayList<>();
        placeholders.add(new Placeholder(1, "Web"));
        placeholders.add(new Placeholder(2, "Mobile"));
        PlaceholderList placeholderList = new PlaceholderList(placeholders);
        Mockito.when(metadataService.getAllPlaceholders()).thenReturn(placeholderList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(placeholderList);

        // Actual
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/v1/metadata/placeholders")
                                                      .accept(MediaType.APPLICATION_JSON)).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Unit test when metadata by type.
     *
     * @throws Exception Exception to throw
     */
    @Test
    void getMetadataByType() throws Exception {

        // Mock
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(new Metadata("brands", "Nike"));
        metadata.add(new Metadata("brands", "PUMA"));
        MetadataList metadataList = new MetadataList(metadata);
        Mockito.when(metadataService.getMetadata(anyString())).thenReturn(metadataList);

        // Expected
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(metadataList);

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
