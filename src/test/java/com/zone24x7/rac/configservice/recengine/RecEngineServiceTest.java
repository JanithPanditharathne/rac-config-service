package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleService;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecService;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlotService;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRuleService;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecEngineServiceTest {

    @InjectMocks
    private RecEngineService recEngineService;


    @Mock
    private RecEngineBundleService recEngineBundleService;

    @Mock
    private RecEngineRuleService recEngineRuleService;

    @Mock
    private RecEngineRecService recEngineRecService;

    @Mock
    private RecEngineRecSlotService recEngineRecSlotService;




    @Nested
    @DisplayName("get bundle config method")
    class GetBundleConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetBundleConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Mock
            when(recEngineBundleService.getBundleConfig()).thenReturn(expected);

            // Actual
            String actual = recEngineService.getBundleConfig();

            // Assert
            assertEquals(expected, actual);
        }

    }



    @Nested
    @DisplayName("get rule config method")
    class GetRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRuleConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Mock
            when(recEngineRuleService.getRuleConfig()).thenReturn(expected);

            // Actual
            String actual = recEngineService.getRuleConfig();

            // Assert
            assertEquals(expected, actual);
        }

    }



    @Nested
    @DisplayName("get rec config method")
    class GetRecConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRecConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Mock
            when(recEngineRecService.getRecsConfig()).thenReturn(expected);

            // Actual
            String actual = recEngineService.getRecsConfig();

            // Assert
            assertEquals(expected, actual);
        }
    }




    @Nested
    @DisplayName("get rec slot config method")
    class GetRecSlotConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetRecSlotsConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Mock
            when(recEngineRecSlotService.getRecSlotsConfig()).thenReturn(expected);

            // Actual
            String actual = recEngineService.getRecSlotsConfig();

            // Assert
            assertEquals(expected, actual);

        }
    }








    @Nested
    @DisplayName("add bundle config method")
    class AddBundleConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddBundleConfigForCorrectValues() {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");

            // Mock
            when(recEngineBundleService.addBundleConfig(anyString())).thenReturn(expected);

            // Actual
            CSResponse actual = recEngineService.addBundleConfig(anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }



    @Nested
    @DisplayName("add rule config method")
    class AddRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddRuleConfigForCorrectValues() {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");

            // Mock
            when(recEngineRuleService.addRuleConfig(anyString())).thenReturn(expected);

            // Actual
            CSResponse actual = recEngineService.addRuleConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
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

            // Mock
            when(recEngineRecService.addRecConfig(anyString())).thenReturn(expected);

            // Actual
            CSResponse actual = recEngineService.addRecConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
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

            // Mock
            when(recEngineRecSlotService.addRecSlotConfig(anyString())).thenReturn(expected);

            // Actual
            CSResponse actual = recEngineService.addRecSlotConfig(Mockito.anyString());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }




    @Nested
    @DisplayName("update bundle config method")
    class UpdateBundleConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateBundleConfigForCorrectValues() throws Exception {

            // Save
            recEngineService.updateBundleConfig();

            // Verify above save method is called.
            verify(recEngineBundleService, times(1)).updateBundleConfig();

        }

    }



    @Nested
    @DisplayName("update rule config method")
    class UpdateRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRuleConfigForCorrectValues() throws Exception {

            // Save
            recEngineService.updateRuleConfig();

            // Verify above save method is called.
            verify(recEngineRuleService, times(1)).updateRuleConfig();
        }


    }




    @Nested
    @DisplayName("update rec config method")
    class UpdateRecConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRecConfigForCorrectValues() throws Exception {

            // Save
            recEngineService.updateRecConfig();

            // Verify above save method is called.
            verify(recEngineRecService, times(1)).updateRecConfig();
        }


    }



    @Nested
    @DisplayName("update rec slot config method")
    class UpdateRecSlotConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRecSlotConfigForCorrectValues() throws Exception {

            // Save
            recEngineService.updateRecSlotConfig();

            // Verify above save method is called.
            verify(recEngineRecSlotService, times(1)).updateRecSlotConfig();
        }


    }





}