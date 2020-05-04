package com.zone24x7.rac.configservice.algorithm;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void getAllAlgorithms() {

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
    void getAlgorithm() {

        // Expected
        Algorithm expected = new Algorithm(100, "Top Trending", "TT algorithm description", "");
        when(algorithmRepository.findById(100)).thenReturn(Optional.of(expected));

        // Assert
        assertEquals(expected, algorithmService.getAlgorithm(100));
    }

    @Test
    void addAlgorithm() {

        // Expected
        String expected = "algorithm added!";
        Algorithm algorithm = new Algorithm(100, "Top Trending", "TT algorithm description", "");
        algorithmRepository.save(algorithm);

        // Verify save method is called.
        verify(algorithmRepository, times(1)).save(algorithm);

        // Actual
        String actual = algorithmService.addAlgorithm(algorithm);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void updateAlgorithm() {

        // Expected
        String expected = "algorithm updated!";
        Algorithm algorithm = new Algorithm(100, "Top Trending", "TT algorithm description", "");
        algorithmRepository.save(algorithm);

        // Verify save method is called.
        verify(algorithmRepository, times(1)).save(algorithm);

        // Actual
        String actual = algorithmService.updateAlgorithm(algorithm, 100);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteAlgorithm() {

        // Expected
        String expected = "algorithm deleted!";
        algorithmRepository.deleteById(100);

        // Verify deleteById method is called.
        verify(algorithmRepository, times(1)).deleteById(100);

        // Actual
        String actual = algorithmService.deleteAlgorithm(100);

        // Assert
        assertEquals(expected, actual);
    }
}