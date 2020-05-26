package com.zone24x7.rac.configservice.recslot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.channel.ChannelRepository;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.page.PageRepository;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderRepository;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecSlotServiceTest {

    @InjectMocks
    private RecSlotService recSlotService;

    @Mock
    private RecSlotRepository recSlotRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private PageRepository pageRepository;

    @Mock
    private PlaceholderRepository placeholderRepository;

    @Mock
    private RecRepository recRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("get all rec slots method")
    void testGetAllRecSlots() {

        // Expected
        List<RecSlot> recSlotsList = new ArrayList<>();
        recSlotsList.add(new RecSlot(4, 5, 2, 8, 1));
        recSlotsList.add(new RecSlot(7, 5, 2, 6, 5));

        // Mock
        // Setup repository method findAllByOrderByIdDesc() return value.
        when(recSlotRepository.findAllByOrderByIdDesc()).thenReturn(recSlotsList);

        Channel channel = mock(Channel.class);
        when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

        Page page = mock(Page.class);
        when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

        Placeholder placeholder = mock(Placeholder.class);
        when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

        Rec rec = mock(Rec.class);
        when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

        RecSlotRec recSlotRec = new RecSlotRec();
        when(modelMapper.map(any(), any())).thenReturn(recSlotRec);

        // Actual
        RecSlotList actual = recSlotService.getAllRecSlots();

        // Assert
        assertEquals(recSlotsList.size(), actual.getRecSlots().size());
    }

    @Nested
    @DisplayName("get rec slot method")
    class GetRecSlot {

        @Test
        @DisplayName("test for invalid rec id")
        void testGetRecSlotForInvalidRecSlotID() {

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                // Get bundle.
                recSlotService.getRecSlot(1);
            });

            String actual = validationException.getMessage();

            // Assert
            assertEquals(Strings.REC_SLOT_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for valid rec id")
        void testGetRecSlotForValidRecSlotID() throws Exception {

            // Mock
            RecSlot recSlot = mock(RecSlot.class);
            when(recSlot.getId()).thenReturn(1);
            when(recSlotRepository.findById(1)).thenReturn(Optional.of(recSlot));

            Channel channel = new Channel();
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = new Page();
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = new Placeholder();
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlotRec recSlotRec = new RecSlotRec();
            when(modelMapper.map(any(), any())).thenReturn(recSlotRec);

            // Expected
            RecSlotDetail expectedRecSlotDetail = new RecSlotDetail(1, channel, page, placeholder, recSlotRec, null);

            ObjectMapper objectMapper = new ObjectMapper();
            String expected = objectMapper.writeValueAsString(expectedRecSlotDetail);

            // Actual
            RecSlotDetail actualRecSlotDetail = recSlotService.getRecSlot(1);
            String actual = objectMapper.writeValueAsString(actualRecSlotDetail);

            // Assert
            assertEquals(expected, actual);
        }
    }
}
