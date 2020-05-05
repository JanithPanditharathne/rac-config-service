package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zone24x7.rac.configservice.util.Strings.BUNDLES;
import static com.zone24x7.rac.configservice.util.Strings.RECS;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOTS;
import static com.zone24x7.rac.configservice.util.Strings.RULES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecEngineServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineService recEngineService;



    @Test
    void addBundleConfig() {

        // Expected
        CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");

        // Set mock rec engine.
        RecEngine recEngine = mock(RecEngine.class);

        // Setup repository method findByConfigType() return value.
        when(recEngineRepository.findByConfigType(BUNDLES)).thenReturn(recEngine);

        // Save
        recEngineRepository.save(recEngine);

        // Verify above save method is called.
        verify(recEngineRepository, times(1)).save(recEngine);

        // Actual
        CSResponse actual = recEngineService.addBundleConfig(Mockito.anyString());

        // Assert
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void addRuleConfig() {

        // Expected
        CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");

        // Set mock rec engine.
        RecEngine recEngine = mock(RecEngine.class);

        // Setup repository method findByConfigType() return value.
        when(recEngineRepository.findByConfigType(RULES)).thenReturn(recEngine);

        // Save
        recEngineRepository.save(recEngine);

        // Verify above save method is called.
        verify(recEngineRepository, times(1)).save(recEngine);

        // Actual
        CSResponse actual = recEngineService.addRuleConfig(Mockito.anyString());

        // Assert
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void addRecConfig() {

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
        CSResponse actual = recEngineService.addRecConfig(Mockito.anyString());

        // Assert
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void addRecSlotConfig() {

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
        CSResponse actual = recEngineService.addRecSlotConfig(Mockito.anyString());

        // Assert
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}