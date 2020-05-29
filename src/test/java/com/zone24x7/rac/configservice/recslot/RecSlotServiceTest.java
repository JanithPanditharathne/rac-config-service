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
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.util.CSResponse;
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

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Mock
    private RecSlotRuleRepository recSlotRuleRepository;

    @Mock
    private RecEngineService recEngineService;

    @Test
    @DisplayName("get all rec slots method")
    void testGetAllRecSlots() {

        // Expected
        List<RecSlot> recSlotsList = new ArrayList<>();
        recSlotsList.add(new RecSlot(5, 2, 8, 1));
        recSlotsList.add(new RecSlot(5, 2, 6, 5));

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

        RecSlotRecDetail recSlotRec = new RecSlotRecDetail();
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
            ValidationException validationException = assertThrows(ValidationException.class, () ->

                // Get bundle.
                recSlotService.getRecSlot(1));

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

            RecSlotRecDetail recSlotRec = new RecSlotRecDetail();
            when(modelMapper.map(any(), any())).thenReturn(recSlotRec);

            // Expected
            RecSlotDetail expectedRecSlotDetail = new RecSlotDetail(1, channel, page, placeholder, recSlotRec, new ArrayList<>());

            ObjectMapper objectMapper = new ObjectMapper();
            String expected = objectMapper.writeValueAsString(expectedRecSlotDetail);

            // Actual
            RecSlotDetail actualRecSlotDetail = recSlotService.getRecSlot(1);
            String actual = objectMapper.writeValueAsString(actualRecSlotDetail);

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("add rec slot method")
    class AddRecSlot {

        @Test
        @DisplayName("test for missing channel")
        void testAddRecSlotForMissingChannel() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                recSlotService.addRecSlot(new RecSlotDetail())
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(CHANNEL_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for invalid channel id")
        void testAddRecSlotForInvalidChannelID() {

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, new Channel(), null, null, null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(CHANNEL_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for missing page")
        void testAddRecSlotForMissingPage() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, null, null, null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(PAGE_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for invalid page id")
        void testAddRecSlotForInvalidPageID() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, new Page(), null, null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(PAGE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for missing placeholder")
        void testAddRecSlotForMissingPlaceholder() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, null, null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(PLACEHOLDER_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for invalid placeholder id")
        void testAddRecSlotForInvalidPlaceholderID() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, new Placeholder(), null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(PLACEHOLDER_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for missing rec")
        void testAddRecSlotForMissingRec() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, null, null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(REC_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for invalid rec id")
        void testAddRecSlotForInvalidRecID() {

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(REC_ID_INVALID, actual);
        }

        // TODO: Add unit test for invalid rules IDs once rules are done.

        @Test
        @DisplayName("test for correct values without rules")
        void testAddRecSlotForCorrectValuesWithoutRules() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            // Actual
            RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), new ArrayList<>());
            CSResponse actual = recSlotService.addRecSlot(recSlotDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recSlotRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateRecSlotConfig();
        }

        @Test
        @DisplayName("test for correct values with rules")
        void testAddRecSlotForCorrectValuesWithRules() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlotRuleDetail recSlotRuleDetail = new RecSlotRuleDetail();
            List<RecSlotRuleDetail> rules = new ArrayList<>();
            rules.add(recSlotRuleDetail);

            RecSlot recSlot = new RecSlot(1,1,1,1);
            recSlot.setId(123);
            when(recSlotRepository.save(any(RecSlot.class))).thenReturn(recSlot);

            List<RecSlotRule> recSlotRules = new ArrayList<>();
            recSlotRules.add(new RecSlotRule(1,1));
            when(recSlotRuleRepository.saveAll(any())).thenReturn(recSlotRules);

            // Actual
            RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), rules);
            CSResponse actual = recSlotService.addRecSlot(recSlotDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recEngineService, times(1)).updateRecSlotConfig();
        }
    }

    @Nested
    @DisplayName("edit rec slot method")
    class EditRecSlot {

        @Test
        @DisplayName("test for invalid rec slot id")
        void testEditRecSlotForInvalidID() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    recSlotService.editRecSlot(1, new RecSlotDetail())
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(REC_SLOT_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testEditRecSlotForCorrectValues() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_UPDATED_SUCCESSFULLY);

            // Mock
            Channel channel = mock(Channel.class);
            when(channelRepository.findById(anyInt())).thenReturn(Optional.of(channel));

            Page page = mock(Page.class);
            when(pageRepository.findById(anyInt())).thenReturn(Optional.of(page));

            Placeholder placeholder = mock(Placeholder.class);
            when(placeholderRepository.findById(anyInt())).thenReturn(Optional.of(placeholder));

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlot recSlot = mock(RecSlot.class);
            when(recSlotRepository.findById(anyInt())).thenReturn(Optional.of(recSlot));

            List<RecSlotRule> recSlotRuleList = new ArrayList<>();
            recSlotRuleList.add(new RecSlotRule());
            when(recSlotRuleRepository.findAllByRecSlotID(anyInt())).thenReturn(recSlotRuleList);

            List<RecSlotRuleDetail> recSlotRuleDetails = new ArrayList<>();
            recSlotRuleDetails.add(new RecSlotRuleDetail());

            // Actual
            RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), recSlotRuleDetails);
            CSResponse actual = recSlotService.editRecSlot(1, recSlotDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recSlotRepository, times(1)).save(any());
            verify(recSlotRuleRepository, times(1)).deleteAll(any());
            verify(recSlotRuleRepository, times(1)).saveAll(any());
            verify(recEngineService, times(1)).updateRecSlotConfig();
        }
    }

    @Nested
    @DisplayName("delete rec slot method")
    class DeleteRecSlot {

        @Test
        @DisplayName("test for invalid rec slot id")
        void testDeleteRecSlotForInvalidID() {

            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    recSlotService.deleteRecSlot(1)
            );

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(REC_SLOT_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testDeleteRecSlotForCorrectValues() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_DELETED_SUCCESSFULLY);

            // Mock
            RecSlot recSlot = mock(RecSlot.class);
            when(recSlotRepository.findById(anyInt())).thenReturn(Optional.of(recSlot));

            List<RecSlotRule> recSlotRuleList = new ArrayList<>();
            recSlotRuleList.add(new RecSlotRule());
            when(recSlotRuleRepository.findAllByRecSlotID(anyInt())).thenReturn(recSlotRuleList);

            // Actual
            CSResponse actual = recSlotService.deleteRecSlot(1);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recSlotRuleRepository, times(1)).deleteAll(any());
            verify(recSlotRepository, times(1)).deleteById(any());
            verify(recEngineService, times(1)).updateRecSlotConfig();
        }
    }
}
