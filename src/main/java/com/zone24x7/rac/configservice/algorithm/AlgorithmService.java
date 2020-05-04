package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    public AlgorithmList getAllAlgorithms() {

        List<Algorithm> algorithms = new ArrayList<>();
        algorithmRepository.findAll().forEach(algorithms::add);
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
        return new CSResponse("FAIL", "CS-1010: Algorithm update failed");
    }


    public String deleteAlgorithm(int id) {
        algorithmRepository.deleteById(id);
        return "algorithm deleted!";
    }
}
