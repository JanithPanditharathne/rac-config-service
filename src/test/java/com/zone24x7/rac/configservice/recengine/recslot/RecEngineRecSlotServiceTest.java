package com.zone24x7.rac.configservice.recengine.recslot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.metadata.Metadata;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.recslot.RecSlotDetail;
import com.zone24x7.rac.configservice.recslot.RecSlotList;
import com.zone24x7.rac.configservice.recslot.RecSlotRecDetail;
import com.zone24x7.rac.configservice.recslot.RecSlotService;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.REC_SLOTS;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecEngineRecSlotServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineRecSlotService recEngineRecSlotService;

    @Mock
    private RecSlotService recSlotService;

    @Mock
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("get rec slot config method")
    class GetRecSlotConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRecSlotsConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(REC_SLOTS)).thenReturn(recEngine);

            // Actual
            String actual = recEngineRecSlotService.getRecSlotsConfig();

            // Assert
            assertEquals(expected, actual);

        }
    }




    @Nested
    @DisplayName("add rec slot config method")
    class AddRecSlotConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddRecSlotConfigForCorrectValues() {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");

            // Set mock rec engine.
            RecEngine recEngine = mock(RecEngine.class);

            // Setup repository method findByConfigType() return value.
            when(recEngineRepository.findByConfigType(REC_SLOTS)).thenReturn(recEngine);

            // Save
            recEngineRepository.save(recEngine);

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(recEngine);

            // Actual
            CSResponse actual = recEngineRecSlotService.addRecSlotConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }




    @Nested
    @DisplayName("update rec slot config method")
    class UpdateRecSlotConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRecSlotConfigForCorrectValues() throws Exception {

            // Mock
            Metadata m = new Metadata();
            List<RecSlotDetail> recSlotDetails = new ArrayList<>();
            recSlotDetails.add(new RecSlotDetail(1, m, m, m, new RecSlotRecDetail(), new ArrayList<>()));
            when(recSlotService.getAllRecSlots(false)).thenReturn(new RecSlotList(recSlotDetails));


            // Save
            recEngineRecSlotService.updateRecSlotConfig();

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(any());


            // Assert rec engine rec slot
            RecEngineRecSlot recEngineRecSlot = new RecEngineRecSlot();
            recEngineRecSlot.setChannel("channel");
            recEngineRecSlot.setPage("page");
            recEngineRecSlot.setPlaceholder("placeholder");
            List<Integer> recIds = new ArrayList<>();
            recIds.add(1);
            recEngineRecSlot.setRecIds(recIds);

            List<Integer> ruleIds = new ArrayList<>();
            ruleIds.add(1);
            recEngineRecSlot.setRuleIds(ruleIds);

            assertEquals("channel", recEngineRecSlot.getChannel());
            assertEquals("page", recEngineRecSlot.getPage());
            assertEquals("placeholder", recEngineRecSlot.getPlaceholder());
            assertEquals(recIds, recEngineRecSlot.getRecIds());
            assertEquals(ruleIds, recEngineRecSlot.getRuleIds());


            // Assert rec engine rec slot list.
            List<RecEngineRecSlot> recEngineRecSlots = new ArrayList<>();
            recEngineRecSlots.add(recEngineRecSlot);
            RecEngineRecSlotList RecEngineRecSlotList = new RecEngineRecSlotList();
            RecEngineRecSlotList.setRecSlots(recEngineRecSlots);

            assertEquals(recEngineRecSlots, RecEngineRecSlotList.getRecSlots());


        }



        @Test
        @DisplayName("test for JsonProcessingException")
        void testUpdateRecSlotConfigForJsonProcessingException() throws JsonProcessingException {

            // Mock
            Metadata m = new Metadata();
            List<RecSlotDetail> recSlotDetails = new ArrayList<>();
            recSlotDetails.add(new RecSlotDetail(1, m, m, m, new RecSlotRecDetail(), new ArrayList<>()));
            when(recSlotService.getAllRecSlots(false)).thenReturn(new RecSlotList(recSlotDetails));

            JsonProcessingException jpe = mock(JsonProcessingException.class);
            when(objectMapper.writeValueAsString(any())).thenThrow(jpe);

            // Exception
            Exception exception = assertThrows(ServerException.class, () -> recEngineRecSlotService.updateRecSlotConfig());

            // Assert
            assertEquals(Strings.REC_ENGINE_REC_SLOT_CONFIG_UPDATE_FAILED, exception.getMessage());

        }


    }






}