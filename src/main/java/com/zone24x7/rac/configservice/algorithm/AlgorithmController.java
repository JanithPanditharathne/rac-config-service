package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
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
    public Algorithm getAlgorithm(@PathVariable int id) throws ValidationException, ServerException {

        if(id == 10) {
            throw new ValidationException("CS-1000:Invalid algorithm id");

        } else if(id == 20) {
            throw new ServerException("CS-1010:Algorithm retrieve failed");

        }
        return algorithmService.getAlgorithm(id);
    }

    @RequestMapping(value = "/algorithms", method = RequestMethod.POST)
    public CSResponse addAlgorithm(@RequestBody Algorithm algorithm) {
        return algorithmService.addAlgorithm(algorithm);
    }

    @RequestMapping(value = "/algorithms/{id}", method = RequestMethod.PUT)
    public CSResponse updateAlgorithm(@PathVariable int id, @RequestBody Algorithm algorithm) {
        return algorithmService.updateAlgorithm(algorithm, id);
    }

    @RequestMapping(value = "/algorithms/{id}", method = RequestMethod.DELETE)
    public String deleteAlgorithm(@PathVariable int id) {
        return algorithmService.deleteAlgorithm(id);
    }

}
