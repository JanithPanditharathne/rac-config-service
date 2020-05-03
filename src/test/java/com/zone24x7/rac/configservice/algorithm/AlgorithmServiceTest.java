package com.zone24x7.rac.configservice.algorithm;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AlgorithmServiceTest {

    @Mock
    private AlgorithmRepository algorithmRepository;

    @InjectMocks
    private AlgorithmService algorithmService = new AlgorithmService();


    @Test
    void getAllAlgorithms() {

        // Expected
        List<Algorithm> algorithmList = new ArrayList<>();
        algorithmList.add(new Algorithm(100, "Top Trending", "TT algorithm description"));
        algorithmList.add(new Algorithm(101, "View View", "VV algorithm description"));
        AlgorithmList expected = new AlgorithmList(algorithmList);

        // Setup repository method findAll() return value.
        when(algorithmRepository.findAll()).thenReturn(algorithmList);

        // Actual
        AlgorithmList actual = algorithmService.getAllAlgorithms();

        // Assert
        assertEquals(expected.getAlgorithms(), actual.getAlgorithms());
    }

    @Test
    void getAlgorithm() {
    }

    @Test
    void addAlgorithm() {
    }

    @Test
    void updateAlgorithm() {
    }

    @Test
    void deleteAlgorithm() {
    }
}