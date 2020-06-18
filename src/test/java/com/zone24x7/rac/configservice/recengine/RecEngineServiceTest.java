package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.bundle.Bundle;
import com.zone24x7.rac.configservice.bundle.BundleAlgorithmDetail;
import com.zone24x7.rac.configservice.bundle.BundleDetail;
import com.zone24x7.rac.configservice.bundle.BundleList;
import com.zone24x7.rac.configservice.bundle.BundleService;
import com.zone24x7.rac.configservice.metadata.Metadata;
import com.zone24x7.rac.configservice.rec.RecBundle;
import com.zone24x7.rac.configservice.rec.RecDetail;
import com.zone24x7.rac.configservice.rec.RecList;
import com.zone24x7.rac.configservice.rec.RecService;
import com.zone24x7.rac.configservice.recengine.algorithm.RecEngineAlgorithm;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundle;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithm;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithmCombineInfo;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleList;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRec;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecList;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecRegularConfig;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecTestConfig;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlot;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlotList;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRule;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRuleList;
import com.zone24x7.rac.configservice.recslot.RecSlotDetail;
import com.zone24x7.rac.configservice.recslot.RecSlotList;
import com.zone24x7.rac.configservice.recslot.RecSlotRecDetail;
import com.zone24x7.rac.configservice.recslot.RecSlotService;
import com.zone24x7.rac.configservice.rule.RuleDetail;
import com.zone24x7.rac.configservice.rule.RuleList;
import com.zone24x7.rac.configservice.rule.RuleService;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BUNDLES;
import static com.zone24x7.rac.configservice.util.Strings.RECS;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOTS;
import static com.zone24x7.rac.configservice.util.Strings.RULES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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


    @Mock
    private BundleService bundleService;

    @Mock
    private RuleService ruleService;

    @Mock
    private RecService recService;

    @Mock
    private RecSlotService recSlotService;




    @Nested
    @DisplayName("get bundle config method")
    class GetBundleConfig {

        @Test
        @DisplayName("test for correct values")
        void testGetBundleConfigForCorrectValues() {

            // Expected
            String expected = "{}";

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setId(1);
            recEngine.setConfigType("test");
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(BUNDLES)).thenReturn(recEngine);

            // Actual
            String actual = recEngineService.getBundleConfig();

            // Assert
            assertEquals(expected, actual);
            assertEquals(1, recEngine.getId());
            assertEquals("test", recEngine.getConfigType());
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

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(RULES)).thenReturn(recEngine);

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

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(RECS)).thenReturn(recEngine);

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

            // Set mock rec engine.
            RecEngine recEngine = new RecEngine();
            recEngine.setConfigJson(expected);

            // Setup repository method findAll() return value.
            when(recEngineRepository.findByConfigType(REC_SLOTS)).thenReturn(recEngine);

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

    }



    @Nested
    @DisplayName("add rule config method")
    class AddRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testAddRuleConfigForCorrectValues() {

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




    @Nested
    @DisplayName("update bundle config method")
    class UpdateBundleConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateBundleConfigForCorrectValues() throws Exception {

            // Mock
            List<Bundle> bundles = new ArrayList<>();
            bundles.add(new Bundle("b1", 5, false, ""));
            BundleList bundleList = new BundleList();
            bundleList.setBundles(bundles);
            when(bundleService.getAllBundles()).thenReturn(bundleList);

            List<BundleAlgorithmDetail> bundleAlgorithmDetails = new ArrayList<>();
            bundleAlgorithmDetails.add(new BundleAlgorithmDetail(100, "Algo1", 0, "", ""));
            BundleDetail bundleDetail = new BundleDetail(1, "b1", 5, false, "",  bundleAlgorithmDetails);
            when(bundleService.getBundle(anyInt())).thenReturn(bundleDetail);


            // Save
            recEngineService.updateBundleConfig();

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(any());


            // Assert rec engine algorithm.
            RecEngineAlgorithm recEngineAlgorithm = new RecEngineAlgorithm();
            recEngineAlgorithm.setId(100);
            recEngineAlgorithm.setName("test");
            recEngineAlgorithm.setType("type");
            recEngineAlgorithm.setDefaultDisplayText("");
            recEngineAlgorithm.setCustomDisplayText("");

            assertEquals(100, recEngineAlgorithm.getId());
            assertEquals(100, recEngineAlgorithm.getId());
            assertEquals(100, recEngineAlgorithm.getId());
            assertEquals(100, recEngineAlgorithm.getId());
            assertEquals(100, recEngineAlgorithm.getId());

            // Assert rec engine bundle algorithm.
            RecEngineBundleAlgorithm recEngineBundleAlgorithm = new RecEngineBundleAlgorithm();
            recEngineBundleAlgorithm.setRank(1);
            recEngineBundleAlgorithm.setAlgorithm(recEngineAlgorithm);

            assertEquals(1, recEngineBundleAlgorithm.getRank());
            assertEquals(recEngineAlgorithm, recEngineBundleAlgorithm.getAlgorithm());


            // Assert rec engine bundle algorithm combine info.
            RecEngineBundleAlgorithmCombineInfo combineInfo = new RecEngineBundleAlgorithmCombineInfo();
            combineInfo.setEnableCombine(false);
            combineInfo.setCombineDisplayText("");

            assertFalse(combineInfo.isEnableCombine());
            assertEquals("", combineInfo.getCombineDisplayText());


            // Assert rec engine bundle
            RecEngineBundle recEngineBundle = new RecEngineBundle();
            recEngineBundle.setId(1);
            recEngineBundle.setName("test");
            recEngineBundle.setType("type");
            recEngineBundle.setDefaultLimit(5);
            List<RecEngineBundleAlgorithm> recEngineBundleAlgorithms = new ArrayList<>();
            recEngineBundleAlgorithms.add(recEngineBundleAlgorithm);
            recEngineBundle.setAlgorithms(recEngineBundleAlgorithms);
            recEngineBundle.setAlgoCombineInfo(combineInfo);


            // Assert rec engine bundle list
            List<RecEngineBundle> recEngineBundles = new ArrayList<>();
            recEngineBundles.add(recEngineBundle);
            RecEngineBundleList recEngineBundleList = new RecEngineBundleList();
            recEngineBundleList.setBundles(recEngineBundles);

            assertEquals(recEngineBundles, recEngineBundleList.getBundles());


        }

    }



    @Nested
    @DisplayName("update rule config method")
    class UpdateRuleConfig {

        @Test
        @DisplayName("test for correct values")
        void testUpdateRuleConfigForCorrectValues() throws Exception {

            // Mock
            List<BaseExpr> baseExprs = new ArrayList<>();
            baseExprs.add(new BaseExpr());
            List<RuleDetail> ruleDetails = new ArrayList<>();
            ruleDetails.add(new RuleDetail(1, "test rule", "BOOST", false, "", baseExprs, "", baseExprs));
            when(ruleService.getAllRules()).thenReturn(new RuleList(ruleDetails));


            // Save
            recEngineService.updateRuleConfig();

            // Verify above save method is called.
            verify(recEngineRepository, times(1)).save(any());


            // Assert rec engine rule.
            RecEngineRule recEngineRule = new RecEngineRule();
            recEngineRule.setId(1);
            recEngineRule.setName("test");
            recEngineRule.setType("type");
            recEngineRule.setIsGlobal(false);
            recEngineRule.setMatchingCondition("");
            recEngineRule.setActionCondition("");

            assertEquals(1, recEngineRule.getId());
            assertEquals("test", recEngineRule.getName());
            assertEquals("type", recEngineRule.getType());
            assertFalse(recEngineRule.getIsGlobal());
            assertEquals("", recEngineRule.getMatchingCondition());
            assertEquals("", recEngineRule.getActionCondition());



            // Assert rec engine rule list.
            List<RecEngineRule> recEngineRules = new ArrayList<>();
            recEngineRules.add(recEngineRule);
            RecEngineRuleList recEngineRuleList = new RecEngineRuleList();
            recEngineRuleList.setRules(recEngineRules);

            assertEquals(recEngineRules, recEngineRuleList.getRules());

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
            recEngineService.updateRecConfig();

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

            RecEngineRecTestConfig testConfig = new RecEngineRecTestConfig();
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
            recEngineService.updateRecSlotConfig();

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


    }





}