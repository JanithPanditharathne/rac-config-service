package com.zone24x7.rac.configservice.recengine.rec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.rec.RecBundle;
import com.zone24x7.rac.configservice.rec.RecDetail;
import com.zone24x7.rac.configservice.rec.RecList;
import com.zone24x7.rac.configservice.rec.RecService;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
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

import static com.zone24x7.rac.configservice.util.Strings.RECS;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecEngineRecServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineRecService recEngineRecService;

    @Mock
    private RecService recService;

    @Mock
    private ObjectMapper objectMapper;



    @Nested
    @DisplayName("get rec config method")
    class GetRecConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRecConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(RECS)).thenReturn(recEngine);

            // Actual
            String actual = recEngineRecService.getRecsConfig();

            // Assert
            assertEquals(expected, actual);
        }
    }



    @Nested
    @DisplayName("add rec config method")
    class AddRecConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddRecConfigForCorrectValues() {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Rec config json successfully added");

            // Set mock rec engine.
            RecEngine recEngine = mock(RecEngine.class);

            // Setup repository method findByConfigType() return value.
            when(recEngineRepository.findByConfigType(RECS)).thenReturn(recEngine);

            // Save
            recEngineRepository.save(recEngine);

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(recEngine);

            // Actual
            CSResponse actual = recEngineRecService.addRecConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }




    @Nested
    @DisplayName("update rec config method")
    class UpdateRecConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRecConfigForCorrectValues() throws Exception {

            // Mock
            List<RecDetail> recDetails = new ArrayList<>();
            recDetails.add(new RecDetail(1, "Test rec", new RecBundle(1, "Test Bundle")));
            when(recService.getAllRecs()).thenReturn(new RecList(recDetails));


            // Save
            recEngineRecService.updateRecConfig();

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(any());


            // Assert rec engine rec
            RecEngineRec recEngineRec = new RecEngineRec();
            recEngineRec.setId(1);
            recEngineRec.setName("test");
            recEngineRec.setType("type");
            recEngineRec.setMatchingCondition("");

            RecEngineRecRegularConfig regularConfig = new RecEngineRecRegularConfig();
            regularConfig.setBundleId(1);
            recEngineRec.setRegularConfig(regularConfig);

            Object testConfig = new Object();
            recEngineRec.setTestConfig(testConfig);

            assertEquals(1, recEngineRec.getId());
            assertEquals("test", recEngineRec.getName());
            assertEquals("type", recEngineRec.getType());
            assertEquals("", recEngineRec.getMatchingCondition());
            assertEquals(regularConfig, recEngineRec.getRegularConfig());
            assertEquals(1, regularConfig.getBundleId());
            assertEquals(testConfig, recEngineRec.getTestConfig());


            // Assert rec engine rec list
            List<RecEngineRec> recEngineRecs = new ArrayList<>();
            recEngineRecs.add(recEngineRec);
            RecEngineRecList recEngineRecList = new RecEngineRecList();
            recEngineRecList.setRecs(recEngineRecs);
            assertEquals(recEngineRecs, recEngineRecList.getRecs());

        }



        @Test
        @DisplayName("test for JsonProcessingException")
        void testUpdateRecConfigForJsonProcessingException() throws JsonProcessingException {

            // Mock
            List<RecDetail> recDetails = new ArrayList<>();
            recDetails.add(new RecDetail(1, "Test rec", new RecBundle(1, "Test Bundle")));
            when(recService.getAllRecs()).thenReturn(new RecList(recDetails));

            JsonProcessingException jpe = mock(JsonProcessingException.class);
            when(objectMapper.writeValueAsString(any())).thenThrow(jpe);

            // Exception
            Exception exception = assertThrows(ServerException.class, () -> recEngineRecService.updateRecConfig());

            // Assert
            assertEquals(Strings.REC_ENGINE_REC_CONFIG_UPDATE_FAILED, exception.getMessage());

        }


    }


}