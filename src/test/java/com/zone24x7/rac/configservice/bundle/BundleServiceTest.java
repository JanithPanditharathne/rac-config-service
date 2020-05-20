package com.zone24x7.rac.configservice.bundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
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
    private RecEngineService recEngineService;

    @InjectMocks
    private BundleService bundleService;

    @Test
    @DisplayName("get all bundles method")
    void getAllBundles() {
        List<Bundle> expectedBundles = new ArrayList<>();
        Bundle bundle = new Bundle();

        expectedBundles.add(bundle);
        when(bundleRepository.findAll()).thenReturn(expectedBundles);

        BundleList actualBundles = bundleService.getAllBundles();

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
    @DisplayName("add bundle")
    class AddBundle {

        @Test
        @DisplayName("missing bundle name")
        void testAddBundleWithMissingName() {

            // Expected
            String expected = Strings.BUNDLE_NAME_CANNOT_BE_NULL;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, null, 2,
                                                                false, "Test",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("empty bundle name")
        void testAddBundleWithEmptyName() {

            // Expected
            String expected = Strings.BUNDLE_NAME_CANNOT_BE_EMPTY;

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
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("missing combined display text")
        void testAddBundleWithMissingCombinedDisplayTextWhenCombinedEnabled() {

            // Expected
            String expected = Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, null,
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("empty combined display text")
        void testAddBundleWithEmptyCombinedDisplayTextWhenCombinedEnabled() {

            // Expected
            String expected = Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending",
                                                                                           "Top Trending Products");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("missing algorithms")
        void testAddBundleWithMissingAlgorithms() {

            // Expected
            String expected = Strings.ALGORITHMS_CANNOT_BE_NULL;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                null);

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("empty algorithms")
        void testAddBundleWithEmptyAlgorithms() {

            // Expected
            String expected = Strings.ALGORITHMS_CANNOT_BE_EMPTY;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                new ArrayList<>());

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("missing custom display text")
        void testAddBundleWithMissingCustomDisplayText() {

            // Expected
            String expected = Strings.BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_NULL;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending", null);

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("empty custom display text")
        void testAddBundleWithEmptyCustomDisplayText() {

            // Expected
            String expected = Strings.BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_EMPTY;

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                           "Top Trending", "");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("invalid algorithm id")
        void testAddBundleWithInvalidAlgorithmID() {

            // Expected
            String expected = Strings.ALGORITHM_ID_DOES_NOT_EXIST + " (1001)";

            Exception exception = assertThrows(ValidationException.class, () -> {

                BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(1001, "Top Trending", 0,
                                                                                           "Top Trending", "Custom");

                BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                true, "Test 1",
                                                                Arrays.asList(bundleAlgorithmDetail));

                bundleService.addBundle(bundleDetail);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("correct values")
        void testAddBundleWithCorrectValues() throws ValidationException, ServerException {

            // Mock
            Algorithm algorithm = mock(Algorithm.class);
            when(algorithmRepository.findById(anyInt())).thenReturn(Optional.of(algorithm));

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, BUNDLE_ADDED_SUCCESSFULLY);


            BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(11, "Top Trending", 0,
                                                                                       "Top Trending", "Custom");

            BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                            true, "Test 1",
                                                            Arrays.asList(bundleAlgorithmDetail));

            // Actual
            CSResponse actual = bundleService.addBundle(bundleDetail);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
            verify(bundleRepository, times(1)).save(any());
            verify(bundleAlgorithmRepository, times(1)).save(any());
            verify(recEngineService, times(1)).updateBundleConfig();
        }

        @Nested
        @DisplayName("edit bundle")
        class EditBundle {

            @Test
            @DisplayName("invalid bundle id")
            void testEditBundleWithInvalidBundleID() {

                // Expected
                String expected = Strings.BUNDLE_ID_INVALID;

                Exception exception = assertThrows(ValidationException.class, () -> {

                    BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                               "Top Trending",
                                                                                               "Top Trending Products");

                    BundleDetail bundleDetail = new BundleDetail(0, null, 2,
                                                                    false, "Test",
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("missing bundle name")
            void testEditBundleWithMissingName() {

                // Expected
                String expected = Strings.BUNDLE_NAME_CANNOT_BE_NULL;

                // Mock
                Bundle bundle = mock(Bundle.class);
                when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

                Exception exception = assertThrows(ValidationException.class, () -> {

                    BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                               "Top Trending",
                                                                                               "Top Trending Products");

                    BundleDetail bundleDetail = new BundleDetail(0, null, 2,
                                                                    false, "Test",
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("empty bundle name")
            void testEditBundleWithEmptyName() {

                // Expected
                String expected = Strings.BUNDLE_NAME_CANNOT_BE_EMPTY;

                // Mock
                Bundle bundle = mock(Bundle.class);
                when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

                Exception exception = assertThrows(ValidationException.class, () -> {

                    BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                               "Top Trending",
                                                                                               "Top Trending Products");

                    BundleDetail bundleDetail = new BundleDetail(0, "", 2,
                                                                    false, "Test",
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("missing combined display text")
            void testEditBundleWithMissingCombinedDisplayTextWhenCombinedEnabled() {

                // Expected
                String expected = Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL;

                // Mock
                Bundle bundle = mock(Bundle.class);
                when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

                Exception exception = assertThrows(ValidationException.class, () -> {

                    BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                               "Top Trending",
                                                                                               "Top Trending Products");

                    BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                    true, null,
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("empty combined display text")
            void testEditBundleWithEmptyCombinedDisplayTextWhenCombinedEnabled() {

                // Expected
                String expected = Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY;

                // Mock
                Bundle bundle = mock(Bundle.class);
                when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

                Exception exception = assertThrows(ValidationException.class, () -> {

                    BundleAlgorithmDetail bundleAlgorithmDetail = new BundleAlgorithmDetail(100, "Top Trending", 0,
                                                                                               "Top Trending",
                                                                                               "Top Trending Products");

                    BundleDetail bundleDetail = new BundleDetail(0, "Bundle 1", 2,
                                                                    true, "",
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("missing algorithms")
            void testEditBundleWithMissingAlgorithms() {

                // Expected
                String expected = Strings.ALGORITHMS_CANNOT_BE_NULL;

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
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("empty algorithms")
            void testEditBundleWithEmptyAlgorithms() {

                // Expected
                String expected = Strings.ALGORITHMS_CANNOT_BE_EMPTY;

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
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("invalid algorithm id")
            void testEditBundleWithInvalidAlgorithmID() {

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
                                                                    Arrays.asList(bundleAlgorithmDetail));

                    bundleService.editBundle(1, bundleDetail);
                });

                // Actual
                String actual = exception.getMessage();

                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("correct values")
            void testEditBundleWithCorrectValues() throws ValidationException, ServerException {

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
                                                                Arrays.asList(bundleAlgorithmDetail));

                // Actual
                CSResponse actual = bundleService.editBundle(1, bundleDetail);

                // Assert
                assertEquals(expected.getStatus(), actual.getStatus());
                assertEquals(expected.getCode(), actual.getCode());
                assertEquals(expected.getMessage(), actual.getMessage());
                verify(bundleRepository, times(1)).save(any());
                verify(bundleAlgorithmRepository, times(1)).delete(any());
                verify(bundleAlgorithmRepository, times(1)).save(any());
                verify(recEngineService, times(1)).updateBundleConfig();
            }
        }
    }

    @Nested
    @DisplayName("delete bundle")
    class DeleteBundle {

        @Test
        @DisplayName("invalid bundle id")
        void testDeleteBundleWithInvalidBundleID() {

            Exception exception = assertThrows(ValidationException.class, () -> {

                bundleService.deleteBundle(1);
            });

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(BUNDLE_ID_INVALID, actual);
        }

        @Test
        @DisplayName("with correct values")
        void testDeleteBundleWithCorrectValues() throws ServerException, ValidationException {

            // Mock
            Bundle bundle = mock(Bundle.class);
            when(bundleRepository.findById(anyInt())).thenReturn(Optional.of(bundle));

            List<BundleAlgorithm> allBundleAlgorithms = new ArrayList<>();
            allBundleAlgorithms.add(mock(BundleAlgorithm.class));
            when(bundleAlgorithmRepository.findAllByBundleID(anyInt())).thenReturn(allBundleAlgorithms);

            // Expected
            CSResponse expected = new CSResponse(SUCCESS, BUNDLE_DELETED_SUCCESSFULLY);

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
