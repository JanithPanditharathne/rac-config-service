//package com.zone24x7.rac.configservice.bundle;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zone24x7.rac.configservice.exception.ValidationException;
//import com.zone24x7.rac.configservice.util.Strings;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class BundleServiceTest {
//
//    @Mock
//    private BundleRepository bundleRepository;
//
//    @InjectMocks
//    private BundleService bundleService;
//
//    @Test
//    @DisplayName("get all bundles method")
//    void getAllBundles() {
//        List<Bundle> expectedBundles = new ArrayList<>();
//        Bundle bundle = new Bundle();
//
//        expectedBundles.add(bundle);
//        when(bundleRepository.findAll()).thenReturn(expectedBundles);
//
//        BundleSummaryListDTO actualBundles = bundleService.getAllBundles();
//
//        assertEquals(expectedBundles.size(), actualBundles.getBundles().size());
//    }
//
//    @Nested
//    @DisplayName("get bundle")
//    class GetBundle {
//
//        @Test
//        @DisplayName("invalid bundle id")
//        void testGetBundleWithInvalidBundleID() {
//
//            ValidationException validationException = assertThrows(ValidationException.class, () -> {
//                bundleService.getBundle(100);
//            });
//
//
//            // Expected
//            String expected = Strings.BUNDLE_ID_INVALID;
//
//            // Actual
//            String actual = validationException.getMessage();
//
//            // Assert
//            assertEquals(expected, actual);
//        }
//
//        @Test
//        @DisplayName("valid bundle id")
//        void testGetBundleWithValidBundleID() throws Exception {
//
//            // Mock
//            Bundle bundle = mock(Bundle.class);
//            when(bundleRepository.findById(100)).thenReturn(Optional.of(bundle));
//
//            BundleAlgorithmDTO bundleAlgorithmDTO = new BundleAlgorithmDTO(100, "Top Trending", 0,
//                                                                           "Top Trending",
//                                                                           "Top Trending Products");
//
//            // Expected
//            ObjectMapper objectMapper = new ObjectMapper();
//            String expected = objectMapper.writeValueAsString(bundleAlgorithmDTO);
//
//            ValidationException validationException = assertThrows(ValidationException.class, () -> {
//                bundleService.getBundle(100);
//            });
//
//            // Actual
//            String actual = validationException.getMessage();
//
//            // Assert
//            assertEquals(expected, actual);
//        }
//    }
//}
