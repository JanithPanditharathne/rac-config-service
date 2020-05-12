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

import static com.zone24x7.rac.configservice.util.Strings.ERROR;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
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

            CSResponse expected = new CSResponse(SUCCESS, "CS-6002: Channel added successfully");

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

            CSResponse expected = new CSResponse(ERROR, "CS-6000: Channel name field is missing");

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

            CSResponse expected = new CSResponse(ERROR, "CS-6001: Channel name already in use");

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
}
