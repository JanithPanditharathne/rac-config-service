package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AlgorithmServiceTest {

    @Mock
    private AlgorithmRepository algorithmRepository;

    @InjectMocks
    private AlgorithmService algorithmService;


    @Test
    @DisplayName("get all algorithms method")
    void testGetAllAlgorithms() {

        // Expected
        List<Algorithm> algorithmList = new ArrayList<>();
        algorithmList.add(new Algorithm(100, "Top Trending", "TT algorithm description", ""));
        algorithmList.add(new Algorithm(101, "View View", "VV algorithm description", ""));
        algorithmList.add(new Algorithm(103, "Best Sellers", "BS algorithm description", ""));

        // Setup repository method findAll() return value.
        when(algorithmRepository.findAll()).thenReturn(algorithmList);

        // Actual
        AlgorithmList actual = algorithmService.getAllAlgorithms();

        // Assert
        assertEquals(3, actual.getAlgorithms().size());
    }




    @Test
    @DisplayName("get algorithm by id method")
    void testGetAlgorithm() throws Exception {

        // Expected
        Algorithm expected = new Algorithm(100, "Top Trending", "TT algorithm description", "");
        when(algorithmRepository.findById(100)).thenReturn(Optional.of(expected));

        // Assert
        assertEquals(expected, algorithmService.getAlgorithm(100));
    }



    @Nested
    @DisplayName("add algorithm method")
    class AddAlgorithm {


        @Test
        @DisplayName("test for zero algorithm id")
        void testAddAlgorithmForZeroAlgorithmID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (zero algorithm id)
                Algorithm algorithm = new Algorithm(0, "Top Trending", "TT algorithm description", "");

                // Add algorithm
                algorithmService.addAlgorithm(algorithm);
            });

            // Expected
            String expected = Strings.ALGORITHM_ID_INVALID;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for negative algorithm id)")
        void testAddAlgorithmForNegativeAlgorithmID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (negative algorithm id)
                Algorithm algorithm = new Algorithm(-1, "Top Trending", "TT algorithm description", "");

                // Add algorithm
                algorithmService.addAlgorithm(algorithm);
            });

            // Expected
            String expected = Strings.ALGORITHM_ID_INVALID;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for already added algorithm id")
        void testAddAlgorithmForUsedAlgorithmID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (negative algorithm id)
                Algorithm algorithm = new Algorithm(100, "Top Trending", "TT algorithm description", "");

                // Setup repository method findAll() return value.
                when(algorithmRepository.findById(algorithm.getId())).thenReturn(Optional.of(algorithm));

                // Add algorithm
                algorithmService.addAlgorithm(algorithm);
            });

            // Expected
            String expected = Strings.ALGORITHM_ID_ALREADY_EXISTS;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("test for null algorithm name")
        void testAddAlgorithmForNullAlgorithmName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (null algorithm name)
                Algorithm algorithm = new Algorithm(100, null, "TT algorithm description", "");

                // Add algorithm
                algorithmService.addAlgorithm(algorithm);
            });

            // Expected
            String expected = Strings.ALGORITHM_NAME_CANNOT_BE_NULL;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for empty algorithm name")
        void testAddAlgorithmForEmptyAlgorithmName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (empty algorithm name)
                Algorithm algorithm = new Algorithm(100, "", "TT algorithm description", "");

                // Add algorithm
                algorithmService.addAlgorithm(algorithm);
            });

            // Expected
            String expected = Strings.ALGORITHM_NAME_CANNOT_BE_EMPTY;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for correct values")
        void testAddAlgorithmForCorrectValues() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_ADD_SUCCESS);

            // Mock
            Algorithm algorithm = new Algorithm(100, "Top Trending", "TT algorithm description", "Top Trending");
            algorithmRepository.save(algorithm);

            // Verify save method is called.
            verify(algorithmRepository, times(1)).save(algorithm);

            // Actual
            CSResponse actual = algorithmService.addAlgorithm(algorithm);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }

    }




    @Nested
    @DisplayName("update algorithm method")
    class UpdateAlgorithm {

        @Test
        @DisplayName("test for zero algorithm id")
        void testUpdateAlgorithmForZeroAlgorithmID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (zero algorithm id)
                Algorithm algorithm = new Algorithm(0, "Top Trending", "TT algorithm description", "");

                // Update algorithm
                algorithmService.updateAlgorithm(algorithm, algorithm.getId());
            });

            // Expected
            String expected = Strings.ALGORITHM_ID_INVALID;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }



        @Test
        @DisplayName("test for null algorithm name")
        void testUpdateAlgorithmForNullAlgorithmName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (null algorithm name)
                Algorithm algorithm = new Algorithm(100, null, "TT algorithm description", "");

                // Update algorithm
                algorithmService.updateAlgorithm(algorithm, algorithm.getId());
            });

            // Expected
            String expected = Strings.ALGORITHM_NAME_CANNOT_BE_NULL;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }


        @Test
        @DisplayName("test for empty algorithm name")
        void testUpdateAlgorithmForEmptyAlgorithmName() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (empty algorithm name)
                Algorithm algorithm = new Algorithm(100, "", "TT algorithm description", "");

                // Update algorithm
                algorithmService.updateAlgorithm(algorithm, algorithm.getId());
            });

            // Expected
            String expected = Strings.ALGORITHM_NAME_CANNOT_BE_EMPTY;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }



        @Test
        @DisplayName("test for correct values")
        void testUpdateAlgorithmForCorrectValues() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_UPDATE_SUCCESS);

            // Mock
            Algorithm algorithm = new Algorithm(100, "Top Trending", "TT algorithm description", "Top Trending");
            algorithmRepository.save(algorithm);

            // Verify save method is called.
            verify(algorithmRepository, times(1)).save(algorithm);

            // Actual
            CSResponse actual = algorithmService.updateAlgorithm(algorithm, algorithm.getId());

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }
    }



    @Nested
    @DisplayName("delete algorithm method")
    class DeleteAlgorithm {

        @Test
        @DisplayName("test for zero algorithm id")
        void testDeleteAlgorithmForZeroAlgorithmID() {
            Exception exception = assertThrows(ValidationException.class, () -> {
                // Mock (zero algorithm id)
                Algorithm algorithm = new Algorithm(0, "Top Trending", "TT algorithm description", "");

                // Delete algorithm
                algorithmService.deleteAlgorithm(algorithm.getId());
            });

            // Expected
            String expected = Strings.ALGORITHM_ID_INVALID;

            // Actual
            String actual = exception.getMessage();

            // Assert
            assertEquals(expected, actual);
        }



        @Test
        @DisplayName("test for correct algorithm id")
        void testDeleteAlgorithm() throws Exception {

            // Expected
            CSResponse expected = new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_DELETE_SUCCESS);

            // Mock
            algorithmRepository.deleteById(100);

            // Verify deleteById method is called.
            verify(algorithmRepository, times(1)).deleteById(100);

            // Actual
            CSResponse actual = algorithmService.deleteAlgorithm(100);

            // Assert
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getMessage(), actual.getMessage());
        }


    }


}