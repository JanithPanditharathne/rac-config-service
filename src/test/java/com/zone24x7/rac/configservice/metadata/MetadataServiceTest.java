package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
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
    @DisplayName("Add channel")
    class AddChannel {

        /**
         * Unit test to add channel successfully.
         *
         * @throws ValidationException Validation exception to throw
         */
        @Test
        void successful() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, CHANNEL_ADDED_SUCCESSFULLY);

            Channel channel = mock(Channel.class);
            when(channel.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addChannel(channel);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

        /**
         * Unit test when channel name is missing.
         *
         */
        @Test
        void missingChannelName() {

            CSResponse expected = new CSResponse(ERROR, CHANNEL_NAME_FIELD_MISSING);

            Channel channel = mock(Channel.class);
            when(channel.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addChannel(channel);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }

        /**
         * Unit test when channel name is already in use.
         *
         */
        @Test
        void existingChannelName() {

            CSResponse expected = new CSResponse(ERROR, CHANNEL_NAME_IN_USE);

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

    /**
     * Unit test to get all channels.
     *
     */
    @Test
    void getAllChannels() {
        List<Channel> expectedChannels = new ArrayList<>();
        Channel channel = new Channel();

        when(modelMapper.map(any(), any())).thenReturn(new MetadataDTO());
        expectedChannels.add(channel);

        when(channelRepository.findAll()).thenReturn(expectedChannels);

        ChannelListDTO actualChannels = metadataService.getAllChannels();

        assertEquals(expectedChannels.size(), actualChannels.getChannels().size());
    }

    @Nested
    @DisplayName("Add page")
    class AddPage {

        /**
         * Unit test to add page successfully.
         *
         * @throws ValidationException Validation exception to throw
         */
        @Test
        void successful() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, PAGE_ADDED_SUCCESSFULLY);

            Page page = mock(Page.class);
            when(page.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addPage(page);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

        /**
         * Unit test when page name is missing.
         *
         */
        @Test
        void missingPageName() {

            CSResponse expected = new CSResponse(ERROR, PAGE_NAME_FIELD_MISSING);

            Page page = mock(Page.class);
            when(page.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPage(page);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }

        /**
         * Unit test when page name is already in use.
         *
         */
        @Test
        void existingPageName() {

            CSResponse expected = new CSResponse(ERROR, PAGE_NAME_IN_USE);

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

    /**
     * Unit test to get all pages.
     *
     */
    @Test
    void getAllPages() {
        List<Page> expectedPages = new ArrayList<>();
        Page page = new Page();

        when(modelMapper.map(any(), any())).thenReturn(new MetadataDTO());
        expectedPages.add(page);

        when(pageRepository.findAll()).thenReturn(expectedPages);

        PageListDTO actualPage = metadataService.getAllPages();

        assertEquals(expectedPages.size(), actualPage.getPages().size());
    }

    @Nested
    @DisplayName("Add placeholder")
    class AddPlaceholder {

        /**
         * Unit test to add placeholder successfully.
         *
         * @throws ValidationException Validation exception to throw
         */
        @Test
        void successful() throws ValidationException {

            CSResponse expected = new CSResponse(SUCCESS, PLACEHOLDER_ADDED_SUCCESSFULLY);

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholder.getName()).thenReturn("Test");

            CSResponse actual = metadataService.addPlaceholder(placeholder);

            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

        /**
         * Unit test when placeholder name is missing.
         *
         */
        @Test
        void missingPlaceholderName() {

            CSResponse expected = new CSResponse(ERROR, PLACEHOLDER_NAME_FIELD_MISSING);

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholder.getName()).thenReturn(null);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                metadataService.addPlaceholder(placeholder);
            });

            String actual = validationException.getMessage();

            assertTrue(actual.contains(expected.getCode()));
            assertTrue(actual.contains(expected.getMessage()));
        }

        /**
         * Unit test when placeholder name is already in use.
         *
         */
        @Test
        void existingPlaceholderName() {

            CSResponse expected = new CSResponse(ERROR, PLACEHOLDER_NAME_IN_USE);

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

    /**
     * Unit test to get all placeholder.
     *
     */
    @Test
    void getAllPlaceholders() {
        List<Placeholder> expectedPlcehoders = new ArrayList<>();
        Placeholder placeholder = new Placeholder();

        when(modelMapper.map(any(), any())).thenReturn(new MetadataDTO());
        expectedPlcehoders.add(placeholder);

        when(placeholderRepository.findAll()).thenReturn(expectedPlcehoders);

        PlaceholderListDTO actualPlaceholders = metadataService.getAllPlaceholders();

        assertEquals(expectedPlcehoders.size(), actualPlaceholders.getPlaceholders().size());
    }
}
