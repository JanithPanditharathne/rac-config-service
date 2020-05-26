package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.channel.ChannelRepository;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.page.PageRepository;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;
import com.zone24x7.rac.configservice.metadata.placeholder.PlaceholderRepository;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
