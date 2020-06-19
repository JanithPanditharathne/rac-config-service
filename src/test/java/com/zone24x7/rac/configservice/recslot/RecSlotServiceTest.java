package com.zone24x7.rac.configservice.recslot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.Metadata;
import com.zone24x7.rac.configservice.metadata.MetadataRepository;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.rule.Rule;
import com.zone24x7.rac.configservice.rule.RuleRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecSlotServiceTest {

    @InjectMocks
    private RecSlotService recSlotService;

    @Mock
    private RecSlotRepository recSlotRepository;

    @Mock
    private RecRepository recRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RecSlotRuleRepository recSlotRuleRepository;

    @Mock
    private RecEngineService recEngineService;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private MetadataRepository metadataRepository;

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

        Metadata channel = mock(Metadata.class);
        when(metadataRepository.findByTypeAndId(CHANNELS, 5)).thenReturn(channel);

        Metadata page = mock(Metadata.class);
        when(metadataRepository.findByTypeAndId(PAGES, 2)).thenReturn(page);

        Metadata placeholder = mock(Metadata.class);
        when(metadataRepository.findByTypeAndId(PLACEHOLDERS, 8)).thenReturn(placeholder);

        Rec rec = mock(Rec.class);
        when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

        RecSlotRule recSlotRule = new RecSlotRule(0,0);
        recSlotRule.setId(1);
        recSlotRule.setRuleID(1);
        recSlotRule.setRecSlotID(1);
        List<RecSlotRule> recSlotRules = new ArrayList<>();
        recSlotRules.add(recSlotRule);
        when(recSlotRuleRepository.findAllByRecSlotID(anyInt())).thenReturn(recSlotRules);

        Rule rule = mock(Rule.class);
        when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(rule);
        when(ruleRepository.findAllByIsGlobal(true)).thenReturn(ruleList);

        RecSlotRecDetail recSlotRec = new RecSlotRecDetail();
        recSlotRec.setName("test");
        when(modelMapper.map(any(), any())).thenReturn(recSlotRec);


        // Actual
        RecSlotList actual = recSlotService.getAllRecSlots(true);

        // Assert
        assertEquals(recSlotsList.size(), actual.getRecSlots().size());



        // Assert rec slot list.
        List<RecSlotDetail> recSlotDetails = new ArrayList<>();
        recSlotDetails.add(new RecSlotDetail());
        RecSlotList recSlotList = new RecSlotList();
        recSlotList.setRecSlots(recSlotDetails);

        assertEquals(recSlotDetails, recSlotList.getRecSlots());



        // Assert rec slot rule
        assertEquals(1, recSlotRule.getId());
        assertEquals(1, recSlotRule.getRuleID());
        assertEquals(1, recSlotRule.getRecSlotID());
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
        @DisplayName("test for negative rec id")
        void testGetRecSlotForNegativeRecSlotID() {

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () ->

                    // Get bundle.
                    recSlotService.getRecSlot(-1));

            String actual = validationException.getMessage();

            // Assert
            assertEquals(Strings.REC_SLOT_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for valid rec id")
        void testGetRecSlotForValidRecSlotID() throws Exception {

            // Mock
            RecSlot recSlot = new RecSlot(1, 1, 1, 1);
            recSlot.setId(1);
            when(recSlotRepository.findById(1)).thenReturn(Optional.of(recSlot));

            Metadata channel = new Metadata();
            channel.setId(1);
            when(metadataRepository.findByTypeAndId(CHANNELS, 1)).thenReturn(channel);

            Metadata page = new Metadata();
            page.setId(1);
            when(metadataRepository.findByTypeAndId(PAGES, 1)).thenReturn(page);

            Metadata placeholder = new Metadata();
            placeholder.setId(1);
            when(metadataRepository.findByTypeAndId(PLACEHOLDERS, 1)).thenReturn(placeholder);

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

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, new Metadata(), null, null, null, null);
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
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

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
            Metadata channel = new Metadata();
            channel.setId(1);
            when(metadataRepository.findByTypeAndId(CHANNELS, 1)).thenReturn(channel);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, new Metadata(), null, null, null);
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
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

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
            Metadata channel = new Metadata();
            channel.setId(1);
            when(metadataRepository.findByTypeAndId(CHANNELS, 1)).thenReturn(channel);

            Metadata page = new Metadata();
            page.setId(1);
            when(metadataRepository.findByTypeAndId(PAGES, 1)).thenReturn(page);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, new Metadata(), null, null);
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
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

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
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), null);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(REC_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for existing similar rec slots")
        void testAddRecSlotForExistingSimilarRecSlots() {

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlot recSlot = new RecSlot();
            recSlot.setChannelID(channel.getId());
            recSlot.setPageID(page.getId());
            recSlot.setPlaceholderID(page.getId());
            recSlot.setRecID(rec.getId());
            List<RecSlot> recSlotList = new ArrayList<>();
            recSlotList.add(recSlot);
            when(recSlotRepository.findAllByChannelIDAndPageIDAndPlaceholderID(anyInt(), anyInt(), anyInt())).thenReturn(recSlotList);

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), new ArrayList<>());
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(SIMILAR_REC_SLOT_ALREADY_EXISTS, actual);
        }

        @Test
        @DisplayName("test for correct values without rules")
        void testAddRecSlotForCorrectValuesWithoutRules() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

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
        @DisplayName("test for invalid rule ID")
        void testAddRecSlotForInvalidRuleID() {

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlotRuleDetail recSlotRuleDetail = new RecSlotRuleDetail();
            recSlotRuleDetail.setId(1);
            recSlotRuleDetail.setName("test");
            List<RecSlotRuleDetail> rules = new ArrayList<>();
            rules.add(recSlotRuleDetail);

            RecSlot recSlot = new RecSlot(1, 1, 1, 1);
            recSlot.setId(123);
            when(recSlotRepository.save(any(RecSlot.class))).thenReturn(recSlot);

            List<RecSlotRule> recSlotRules = new ArrayList<>();
            recSlotRules.add(new RecSlotRule(1, 1));
            when(recSlotRuleRepository.saveAll(any())).thenReturn(recSlotRules);

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), rules);
                recSlotService.addRecSlot(recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertTrue(actual.contains(RULE_ID_INVALID));


            // Assert rec slot rec detail
            assertEquals(1, recSlotRuleDetail.getId());
            assertEquals("test", recSlotRuleDetail.getName());
        }

        @Test
        @DisplayName("test for correct values with rules")
        void testAddRecSlotForCorrectValuesWithRules() throws ServerException, ValidationException {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_ADDED_SUCCESSFULLY);

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlotRuleDetail recSlotRuleDetail = new RecSlotRuleDetail();
            recSlotRuleDetail.setId(1);
            List<RecSlotRuleDetail> rules = new ArrayList<>();
            rules.add(recSlotRuleDetail);

            Rule rule = mock(Rule.class);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            RecSlot recSlot = new RecSlot(1, 1, 1, 1);
            recSlot.setId(123);
            when(recSlotRepository.save(any(RecSlot.class))).thenReturn(recSlot);

            List<RecSlotRule> recSlotRules = new ArrayList<>();
            recSlotRules.add(new RecSlotRule(1, 1));
            when(recSlotRuleRepository.saveAll(any())).thenReturn(recSlotRules);

            // Actual
            RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), rules);
            CSResponse actual = recSlotService.addRecSlot(recSlotDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recSlotRepository, times(1)).save(any());
            verify(recSlotRuleRepository, times(1)).saveAll(any());
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
        @DisplayName("test for existing similar rec slots")
        void testEditRecSlotForExistingSimilarRecSlots() {

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlot recSlot = mock(RecSlot.class);
            when(recSlotRepository.findById(anyInt())).thenReturn(Optional.of(recSlot));

            List<RecSlot> recSlotList = new ArrayList<>();
            recSlotList.add(recSlot);
            when(recSlotRepository.findAllByChannelIDAndPageIDAndPlaceholderID(anyInt(), anyInt(), anyInt())).thenReturn(recSlotList);

            // Actual
            ValidationException validationException = assertThrows(ValidationException.class, () -> {

                RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), new ArrayList<>());
                recSlotService.editRecSlot(1, recSlotDetail);
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(SIMILAR_REC_SLOT_ALREADY_EXISTS, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testEditRecSlotForCorrectValues() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, REC_SLOT_UPDATED_SUCCESSFULLY);

            // Mock
            Metadata channel = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(channel);

            Metadata page = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(page);

            Metadata placeholder = mock(Metadata.class);
            when(metadataRepository.findByTypeAndId(anyString(), anyInt())).thenReturn(placeholder);

            Rec rec = mock(Rec.class);
            when(recRepository.findById(anyInt())).thenReturn(Optional.of(rec));

            RecSlot recSlot = mock(RecSlot.class);
            when(recSlotRepository.findById(anyInt())).thenReturn(Optional.of(recSlot));

            List<RecSlotRule> recSlotRuleList = new ArrayList<>();
            recSlotRuleList.add(new RecSlotRule());
            when(recSlotRuleRepository.findAllByRecSlotID(anyInt())).thenReturn(recSlotRuleList);

            RecSlotRuleDetail recSlotRuleDetail = new RecSlotRuleDetail();
            recSlotRuleDetail.setId(1);
            List<RecSlotRuleDetail> rules = new ArrayList<>();
            rules.add(recSlotRuleDetail);

            Rule rule = mock(Rule.class);
            when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

            // Actual
            RecSlotDetail recSlotDetail = new RecSlotDetail(0, channel, page, placeholder, new RecSlotRecDetail(), rules);
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
