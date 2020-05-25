package com.zone24x7.rac.configservice.bundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class BundleServiceTest {

    @Mock
    private BundleRepository bundleRepository;

    @Mock
    private BundleAlgorithmRepository bundleAlgorithmRepository;

    @Mock
    private AlgorithmRepository algorithmRepository;

    @Mock
    private RecRepository recRepository;

    @Mock
    private RecEngineService recEngineService;

    @InjectMocks
    private BundleService bundleService;

    @Test
    @DisplayName("get all bundles method")
    void testGetAllBundles() {

        // Expected
        List<Bundle> bundleList = new ArrayList<>();
        bundleList.add(new Bundle("Bundle 1", 5, false, null));
        bundleList.add(new Bundle("Bundle 2", 5, true, "Sample text"));

        // Setup repository method findAllByOrderByIdDesc() return value.
        when(bundleRepository.findAllByOrderByIdDesc()).thenReturn(bundleList);

        // Actual
        BundleList actual = bundleService.getAllBundles();

        // Assert
        assertEquals(bundleList.size(), actual.getBundles().size());
    }

    @Nested
    @DisplayName("get bundle method")
    class GetBundle {

        @Test
        @DisplayName("test for invalid bundle id")
        void testGetBundleForInvalidBundleID() {

            ValidationException validationException = assertThrows(ValidationException.class, () -> {
                // Mock (invalid bundle id)
                Bundle bundle = new Bundle("Bundle 1", 5, false, null);
                bundle.setId(99999);
                when(bundleRepository.findById(bundle.getId())).thenReturn(Optional.empty());

                // Get bundle.
                bundleService.getBundle(bundle.getId());
            });

            // Actual
            String actual = validationException.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for valid bundle id")
        void testGetBundleForValidBundleID() throws Exception {

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
            BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                       "Top Trending",
                                                                                       "Top Trending Products");

            BundleDetail expectedBundleDetail = new BundleDetail(1, "Bundle1", 2,
                                                                    false, "Test",
                                                                    Arrays.asList(bundleAlgorithmDetail));

            ObjectMapper objectMapper = new ObjectMapper();
            String expected = objectMapper.writeValueAsString(expectedBundleDetail);

            // Actual
            BundleDetail bundleDetail = bundleService.getBundle(1);
            String actual = objectMapper.writeValueAsString(bundleDetail);

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("add bundle method")
    class AddBundle {

        @Test
        @DisplayName("test for null bundle name")
        void testAddBundleForNullBundleName() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");
                BundleDetail bundleDetail = new BundleDetail(0, null, 2, false, "Test",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_NAME_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty bundle name")
        void testAddBundleForEmptyBundleName() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "", 2,
                                                                false, "Test",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_NAME_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for null combined display text when combined enabled is true")
        void testAddBundleForNullCombinedDisplayTextWhenCombinedEnabled() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, null,
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty combined display text when combined enabled is true")
        void testAddBundleForEmptyCombinedDisplayTextWhenCombinedEnabled() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for null algorithms")
        void testAddBundleForNullAlgorithms() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                null);

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.ALGORITHMS_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty algorithms")
        void testAddBundleForEmptyAlgorithms() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                new ArrayList<>());

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.ALGORITHMS_CANNOT_BE_EMPTY, actual);
        }



        @Test
        @DisplayName("test for invalid algorithm id")
        void testAddBundleForInvalidAlgorithmID() {

            // Expected
            String expected = Strings.ALGORITHM_ID_DOES_NOT_EXIST + " (1001)";

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(1001, "Top Trending", 0,
                                                                                           "Top Trending", "Custom");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testAddBundleForCorrectValues() throws ValidationException, ServerException {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, BUNDLE_ADDED_SUCCESSFULLY);


            BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(11, "Top Trending", 0,
                                                                                       "Top Trending", "Custom");

            BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2, true, "Test 1",
                    Collections.singletonList(bundleAlgorithmDetail));

            // Mock algorithm.
            Algorithm algorithm = new Algorithm();
            when(algorithmRepository.findById(anyInt())).thenReturn(Optional.of(algorithm));

            // Mock bundle.
            Bundle bundle = new Bundle("Bundle 1", 5, false, null);
            bundle.setId(123);
            when(bundleRepository.save(any(Bundle.class))).thenReturn(bundle);

            // Mock bundle algorithm
            BundleAlgorithm bundleAlgorithm = new BundleAlgorithm(bundle.getId(), 100, "Sample text", 0);
            bundleAlgorithmRepository.save(bundleAlgorithm);

            // Verify bundle-algorithm save method is called.
            verify(bundleAlgorithmRepository, times(1)).save(bundleAlgorithm);


            // Actual
            CSResponse actual = bundleService.addBundle(bundleDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(recEngineService, times(1)).updateBundleConfig();
        }


    }



    @Nested
    @DisplayName("edit bundle method")
    class EditBundle {

        @Test
        @DisplayName("test for invalid bundle id")
        void testEditBundleForInvalidBundleID() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                        "Top Trending",
                        "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, null, 2,
                        false, "Test",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("test for null bundle name")
        void testEditBundleForNullBundleName() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                        "Top Trending",
                        "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, null, 2,
                        false, "Test",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_NAME_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty bundle name")
        void testEditBundleForEmptyBundleName() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                        "Top Trending",
                        "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "", 2,
                        false, "Test",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_NAME_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for null combined display text when combine enabled is true")
        void testEditBundleForNullCombinedDisplayTextWhenCombinedEnabled() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                        "Top Trending",
                        "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                        true, null,
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty combined display text when combine enabled is true")
        void testEditBundleForEmptyCombinedDisplayTextWhenCombinedEnabled() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                        "Top Trending",
                        "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                        true, "",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for null algorithms")
        void testEditBundleForNullAlgorithms() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                        true, "Test 1",
                        null);

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.ALGORITHMS_CANNOT_BE_NULL, actual);
        }

        @Test
        @DisplayName("test for empty algorithms")
        void testEditBundleForEmptyAlgorithms() {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                        true, "Test 1",
                        new ArrayList<>());

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(Strings.ALGORITHMS_CANNOT_BE_EMPTY, actual);
        }

        @Test
        @DisplayName("test for invalid algorithm id")
        void testEditBundleForInvalidAlgorithmID() {

            // Expected
            String expected = Strings.ALGORITHM_ID_DOES_NOT_EXIST + " (1001)";

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(1001, "Top Trending", 0,
                        "Top Trending", "Custom");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                        true, "Test 1",
                        Collections.singletonList(bundleAlgorithmDetail));

                bundleService.editBundle(1, bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for correct values")
        void testEditBundleForCorrectValues() throws ValidationException, ServerException {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            Algorithm algorithm = mock(Algorithm.class);
            when(algorithmRepository.findById(anyInt())).thenReturn(Optional.of(algorithm));

            List<BundleAlgorithm> allBundleAlgorithms = new ArrayList<>();
            allBundleAlgorithms.add(mock(BundleAlgorithm.class));
            when(bundleAlgorithmRepository.findAllByBundleID(anyInt())).thenReturn(allBundleAlgorithms);

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, BUNDLE_UPDATED_SUCCESSFULLY);


            BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(11, "Top Trending", 0,
                    "Top Trending", "Custom");

            BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                    true, "Test 1",
                    Collections.singletonList(bundleAlgorithmDetail));

            // Actual
            CSResponse actual = bundleService.editBundle(1, bundleDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(bundleRepository, times(1)).save(any());
            verify(bundleAlgorithmRepository, times(1)).delete(any());
            verify(bundleAlgorithmRepository, times(1)).saveAll(any());
            verify(recEngineService, times(1)).updateBundleConfig();
        }
    }




    @Nested
    @DisplayName("delete bundle method")
    class DeleteBundle {

        @Test
        @DisplayName("test for invalid bundle id")
        void testDeleteBundleForInvalidBundleID() {

            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (invalid bundle id)
                Bundle bundle = new Bundle("Invalid bundle", 5, false, "");
                bundle.setId(99999);

                // Setup repository method findById() return value.
                when(bundleRepository.findById(bundle.getId())).thenReturn(Optional.empty());

                // Delete bundle.
                bundleService.deleteBundle(bundle.getId());
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(BUNDLE_ID_INVALID, actual);
        }


        @Test
        @DisplayName("test for already used bundle id")
        void testDeleteBundleForAlreadyUsedBundleID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock
                Bundle bundle = new Bundle("Test bundle", 5, false, "");
                bundle.setId(1);

                // Setup repository method findById() return value.
                when(bundleRepository.findById(bundle.getId())).thenReturn(Optional.of(bundle));

                // Setup repository method findAllByAlgorithmID() return value.
                List<Rec> recList = new ArrayList<>();
                recList.add(new Rec(1, "Test rec", bundle.getId()));
                when(recRepository.findAllByBundleID(bundle.getId())).thenReturn(recList);

                // Delete algorithm
                bundleService.deleteBundle(bundle.getId());
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(BUNDLE_ID_ALREADY_USE_IN_RECS, actual);
        }

        @Test
        @DisplayName("test for correct bundle id")
        void testDeleteBundleForCorrectBundleID() throws ServerException, ValidationException {

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, BUNDLE_DELETED_SUCCESSFULLY);

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));
            when(recRepository.findAllByBundleID(bundle.getId())).thenReturn(new ArrayList<>());

            List<BundleAlgorithm> allBundleAlgorithms = new ArrayList<>();
            allBundleAlgorithms.add(mock(BundleAlgorithm.class));
            when(bundleAlgorithmRepository.findAllByBundleID(anyInt())).thenReturn(allBundleAlgorithms);

            // Actual
            CSResponse actual = bundleService.deleteBundle(1);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(bundleAlgorithmRepository, times(1)).delete(any());
            verify(bundleRepository, times(1)).delete(any());
            verify(recEngineService, times(1)).updateBundleConfig();
        }
    }
}
