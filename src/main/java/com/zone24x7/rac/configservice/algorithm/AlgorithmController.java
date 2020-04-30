package com.zone24x7.rac.configservice.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;


    @RequestMapping("/algorithms")
    public AlgorithmList getAllAlgorithms() {
        return algorithmService.getAllAlgorithms();
    }

    @RequestMapping("/algorithms/{id}")
    public Algorithm getAlgorithm(@PathVariable int id) {
        return algorithmService.getAlgorithm(id);
    }

    @RequestMapping(value = "/algorithms", method = RequestMethod.POST)
    public String addAlgorithm(@RequestBody Algorithm algorithm) {
        return algorithmService.addAlgorithm(algorithm);
    }

    @RequestMapping(value = "/algorithms/{id}", method = RequestMethod.PUT)
    public String updateAlgorithm(@PathVariable int id, @RequestBody Algorithm algorithm) {
        return algorithmService.updateAlgorithm(algorithm, id);
    }

    @RequestMapping(value = "/algorithms/{id}", method = RequestMethod.DELETE)
    public String deleteAlgorithm(@PathVariable int id) {
        return algorithmService.deleteAlgorithm(id);
    }

}
