package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private ModelMapper modelMapper;



    /**
     * Get all algorithms.
     *
     * @return algorithm list.
     */
    @GetMapping("/algorithms")
    public AlgorithmList getAllAlgorithms() {
        return algorithmService.getAllAlgorithms();
    }


    /**
     * Get algorithm detail by id.
     *
     * @param id algorithm id.
     * @return algorithm details.
     * @throws ValidationException for invalid algorithm id.
     */
    @GetMapping("/algorithms/{id}")
    public AlgorithmDTO getAlgorithm(@PathVariable int id) throws ValidationException {
        return modelMapper.map(algorithmService.getAlgorithm(id), AlgorithmDTO.class);
    }


    /**
     * Add new algorithm.
     *
     * @param algorithmDTO new algorithm details that need to add
     * @return status response
     */
    @PostMapping("/algorithms")
    public CSResponse addAlgorithm(@RequestBody AlgorithmDTO algorithmDTO) throws ValidationException {
        return algorithmService.addAlgorithm(modelMapper.map(algorithmDTO, Algorithm.class));
    }


    /**
     * Update algorithm details.
     *
     * @param id algorithm id.
     * @param algorithmDTO algorithm details.
     * @return status response.
     */
    @PutMapping("/algorithms/{id}")
    public CSResponse updateAlgorithm(@PathVariable int id, @RequestBody AlgorithmDTO algorithmDTO) throws ValidationException {
        return algorithmService.updateAlgorithm(modelMapper.map(algorithmDTO, Algorithm.class), id);
    }


    /**
     * Delete algorithm.
     *
     * @param id algorithm id to delete.
     * @return status response
     */
    @DeleteMapping("/algorithms/{id}")
    public CSResponse deleteAlgorithm(@PathVariable int id) throws ValidationException {
        return algorithmService.deleteAlgorithm(id);
    }

}
