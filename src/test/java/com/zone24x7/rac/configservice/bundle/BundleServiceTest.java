package com.zone24x7.rac.configservice.bundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BundleServiceTest {

    @Mock
    private BundleRepository bundleRepository;

    @Mock
    private BundleAlgorithmRepository bundleAlgorithmRepository;

    @Mock
    private AlgorithmRepository algorithmRepository;

    @InjectMocks
    private BundleService bundleService;

    @Test
    @DisplayName("get all bundles method")
    void getAllBundles() {
        List<Bundle> expectedBundles = new ArrayList<>();
        Bundle bundle = new Bundle();

        expectedBundles.add(bundle);
        when(bundleRepository.findAll()).thenReturn(expectedBundles);

        BundleSummaryListDTO actualBundles = bundleService.getAllBundles();

        assertEquals(expectedBundles.size(), actualBundles.getBundles().size());
    }

    @Nested
    @DisplayName("get bundle")
    class GetBundle {

        @Test
        @DisplayName("invalid bundle id")
        void testGetBundleWithInvalidBundleID() {

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                bundleService.getBundle(100);
            });


            // Expected
            String expected = Strings.BUNDLE_ID_INVALID;

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("valid bundle id")
        void testGetBundleWithValidBundleID() throws Exception {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundle.getId()).thenReturn(100);
            when(bundle.getName()).thenReturn("Bundle1");
            when(bundle.getDefaultLimit()).thenReturn(2);
            when(bundle.isCombineEnabled()).thenReturn(false);
            when(bundle.getCombineDisplayText()).thenReturn("Test");
            when(bundleRepository.findById(1)).thenReturn(Optional.of(bundle));

            BundleAlgorithm bundleAlgorithm = new BundleAlgorithm();
            bundleAlgorithm.setBundleID(1);
            bundleAlgorithm.setAlgorithmID(100);
            bundleAlgorithm.setCustomDisplayText("Top Trending Products");

            List<BundleAlgorithm> bundleAlgorithmList = new ArrayList<>();
            bundleAlgorithmList.add(bundleAlgorithm);
            when(bundleAlgorithmRepository.findAllByBundleID(1)).thenReturn(bundleAlgorithmList);

            Algorithm algorithm = mock(Algorithm.class);
            when(algorithm.getName()).thenReturn("Top Trending");
            when(algorithm.getDefaultDisplayText()).thenReturn("Top Trending");
            when(algorithmRepository.findById(100)).thenReturn(Optional.of(algorithm));

            // Expected
            BundleAlgorithmDTO bundleAlgorithmDTO = new BundleAlgorithmDTO(100, "Top Trending", 0,
                                                                           "Top Trending",
                                                                           "Top Trending Products");

            BundleDetailDTO expectedBundleDetailDTO = new BundleDetailDTO("1", "Bundle1", 2,
                                                                          false, "Test",
                                                                          Arrays.asList(bundleAlgorithmDTO));

            ObjectMapper objectMapper = new ObjectMapper();
            String expected = objectMapper.writeValueAsString(expectedBundleDetailDTO);

            // Actual
            BundleDetailDTO bundleDetailDTO = bundleService.getBundle(1);
            String actual = objectMapper.writeValueAsString(bundleDetailDTO);

            // Assert
            assertEquals(expected, actual);
        }
    }
}
