package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.channel.ChannelDTO;
import com.zone24x7.rac.configservice.metadata.channel.ChannelList;
import com.zone24x7.rac.configservice.metadata.channel.ChannelRepository;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.page.PageDTO;
import com.zone24x7.rac.configservice.metadata.page.PageList;
import com.zone24x7.rac.configservice.metadata.page.PageRepository;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderDTO;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderList;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderRepository;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MetadataServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private PageRepository pageRepository;

    @Mock
    private PlaceholderRepository placeholderRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MetadataService metadataService;

    @Nested
    @DisplayName("add channel method")
    class AddChannel {


        @Test
        @DisplayName("test for correct values")
        void testAddChannelForCorrectValues() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, CHANNEL_ADDED_SUCCESSFULLY);

            Channel channel = mock(Channel.class);
            when(channel.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addChannel(channel);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }


        @Test
        @DisplayName("test for missing channel name")
        void testAddChannelForMissingChannelName() {

            CSResponse expected = new CSResponse(ERROR, CHANNEL_NAME_CANNOT_BE_NULL);

            Channel channel = mock(Channel.class);
            when(channel.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addChannel(channel);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }


        @Test
        @DisplayName("test for existing channel name")
        void testAddChannelForExistingChannelName() {

            CSResponse expected = new CSResponse(ERROR, CHANNEL_NAME_ALREADY_EXISTS);

            Channel channel = mock(Channel.class);
            when(channel.getName()).thenReturn("Test");
            when(channelRepository.findByNameIgnoreCase(anyString())).thenReturn(channel);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addChannel(channel);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }
    }


    @Test
    @DisplayName("get all channels method")
    void getAllChannels() {
        List<Channel> expectedChannels = new ArrayList<>();
        Channel channel = new Channel();

        when(modelMapper.map(any(), any())).thenReturn(new ChannelDTO());
        expectedChannels.add(channel);

        when(channelRepository.findAll()).thenReturn(expectedChannels);

        ChannelList actualChannels = metadataService.getAllChannels();

        assertEquals(expectedChannels.size(), actualChannels.getChannels().size());
    }

    @Nested
    @DisplayName("add page method")
    class AddPage {


        @Test
        @DisplayName("test for correct values")
        void testAddPageForCorrectValues() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, PAGE_ADDED_SUCCESSFULLY);

            Page page = mock(Page.class);
            when(page.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addPage(page);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }


        @Test
        @DisplayName("test for missing page name")
        void testAddPageForMissingPageName() {

            CSResponse expected = new CSResponse(ERROR, PAGE_NAME_CANNOT_BE_NULL);

            Page page = mock(Page.class);
            when(page.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPage(page);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }


        @Test
        @DisplayName("test for existing page name")
        void testAddPageForExistingPageName() {

            CSResponse expected = new CSResponse(ERROR, PAGE_NAME_ALREADY_EXISTS);

            Page page = mock(Page.class);
            when(page.getName()).thenReturn("Test");
            when(pageRepository.findByNameIgnoreCase(anyString())).thenReturn(page);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPage(page);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }
    }


    @Test
    @DisplayName("get all pages method")
    void getAllPages() {
        List<Page> expectedPages = new ArrayList<>();
        Page page = new Page();

        when(modelMapper.map(any(), any())).thenReturn(new PageDTO());
        expectedPages.add(page);

        when(pageRepository.findAll()).thenReturn(expectedPages);

        PageList actualPage = metadataService.getAllPages();

        assertEquals(expectedPages.size(), actualPage.getPages().size());
    }

    @Nested
    @DisplayName("add placeholder method")
    class AddPlaceholder {


        @Test
        @DisplayName("test for correct values")
        void testAddPlaceholderForCorrectValues() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, PLACEHOLDER_ADDED_SUCCESSFULLY);

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholder.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addPlaceholder(placeholder);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }


        @Test
        @DisplayName("test for missing placeholder name")
        void testAddPlaceholderForMissingPlaceholderName() {

            CSResponse expected = new CSResponse(ERROR, PLACEHOLDER_NAME_CANNOT_BE_NULL);

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholder.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPlaceholder(placeholder);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }


        @Test
        @DisplayName("test for existing placeholder name")
        void testAddPlaceholderForExistingPlaceholderName() {

            CSResponse expected = new CSResponse(ERROR, PLACEHOLDER_NAME_ALREADY_EXISTS);

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholder.getName()).thenReturn("Test");
            when(placeholderRepository.findByNameIgnoreCase(anyString())).thenReturn(placeholder);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPlaceholder(placeholder);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }
    }


    @Test
    @DisplayName("get all placeholder method")
    void getAllPlaceholders() {
        List<Placeholder> expectedPlcehoders = new ArrayList<>();
        Placeholder placeholder = new Placeholder();

        when(modelMapper.map(any(), any())).thenReturn(new PlaceholderDTO());
        expectedPlcehoders.add(placeholder);

        when(placeholderRepository.findAll()).thenReturn(expectedPlcehoders);

        PlaceholderList actualPlaceholders = metadataService.getAllPlaceholders();

        assertEquals(expectedPlcehoders.size(), actualPlaceholders.getPlaceholders().size());
    }
}
