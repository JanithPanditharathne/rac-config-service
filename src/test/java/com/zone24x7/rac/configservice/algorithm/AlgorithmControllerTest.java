package com.zone24x7.rac.configservice.algorithm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlgorithmControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAllAlgorithms() throws Exception {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                new URI("http://localhost:" + port + "/v1/algorithms").toString(),
                String.class
        );

        String str = "{\"algorithms\":[]}";
        assertEquals(str, responseEntity.getBody());
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