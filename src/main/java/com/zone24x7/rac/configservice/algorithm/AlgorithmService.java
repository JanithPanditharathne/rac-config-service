package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    // Logger
    Logger logger = LoggerFactory.getLogger(AlgorithmService.class);



    public AlgorithmList getAllAlgorithms() {

        List<Algorithm> algorithms = new ArrayList<>();
        algorithmRepository.findAll().forEach(algorithms::add);

        logger.error("count: " + algorithms.size());

        return new AlgorithmList(algorithms);
    }

    public Algorithm getAlgorithm(int id) {
       return algorithmRepository.findById(id).get();
    }

    public CSResponse addAlgorithm(Algorithm algorithm) {
        algorithmRepository.save(algorithm);
        return new CSResponse("SUCCESS", "CS-1001: Algorithm added successfully");
    }

    public CSResponse updateAlgorithm(Algorithm algorithm, int id) {
        algorithm.setId(id);
        algorithmRepository.save(algorithm);
        return new CSResponse("SUCCESS", "CS-1009: Algorithm updated successfully");
    }


    public String deleteAlgorithm(int id) {
        algorithmRepository.deleteById(id);
        return "algorithm deleted!";
    }
}
