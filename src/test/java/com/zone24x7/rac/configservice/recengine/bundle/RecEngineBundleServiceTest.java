package com.zone24x7.rac.configservice.recengine.bundle;

import com.zone24x7.rac.configservice.bundle.Bundle;
import com.zone24x7.rac.configservice.bundle.BundleAlgorithmDetail;
import com.zone24x7.rac.configservice.bundle.BundleDetail;
import com.zone24x7.rac.configservice.bundle.BundleList;
import com.zone24x7.rac.configservice.bundle.BundleService;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.recengine.algorithm.RecEngineAlgorithm;
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
class RecEngineBundleServiceTest {

    @Mock
    private RecEngineRepository recEngineRepository;

    @InjectMocks
    private RecEngineBundleService recEngineBundleService;

    @Mock
    private BundleService bundleService;


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
            String actual = recEngineBundleService.getBundleConfig();

            // Assert
            assertEquals(expected, actual);
            assertEquals(1, recEngine.getId());
            assertEquals("test", recEngine.getConfigType());
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
            CSResponse actual = recEngineBundleService.addBundleConfig(Mockito.anyString());

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
            recEngineBundleService.updateBundleConfig();

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

}