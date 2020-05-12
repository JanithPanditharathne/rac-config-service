package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    // Logger
    Logger logger = LoggerFactory.getLogger(AlgorithmService.class);


    /**
     * Return algorithm list.
     *
     * @return algorithm list.
     */
    public AlgorithmList getAllAlgorithms() {
        List<Algorithm> algorithms = new ArrayList<>();
        algorithmRepository.findAll().forEach(algorithms::add);
        return new AlgorithmList(algorithms);
    }


    /**
     * Get algorithm details by id.
     *
     * @param id algorithm id.
     * @return algorithm details.
     */
    public Algorithm getAlgorithm(int id) throws ValidationException {

        // Find given algorithm id in db.
        Optional<Algorithm> algorithmOptional = algorithmRepository.findById(id);

        // If algorithm found, return its details.
        if(algorithmOptional.isPresent()) {
            return algorithmOptional.get();

        } else {
            // Algorithm not found in db.
            // Return invalid algorithm id error.
            throw new ValidationException(Strings.ALGORITHM_ID_INVALID);
        }
    }

    /**
     * Add new algorithm.
     *
     * @param algorithm new algorithm details.
     * @return status response.
     */
    public CSResponse addAlgorithm(Algorithm algorithm) throws ValidationException {

        // Validate algorithm id.
        AlgorithmValidations.validateID(algorithm.getId());

        // Validate algorithm id is already exists.
        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(algorithm.getId());
        if(optionalAlgorithm.isPresent()) {
            throw new ValidationException(Strings.ALGORITHM_ID_ALREADY_EXISTS);
        }

        // Validate algorithm name.
        AlgorithmValidations.validateName(algorithm.getName());

        // Save new algorithm.
        algorithmRepository.save(algorithm);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_ADD_SUCCESS);

    }


    /**
     * Update algorithm details.
     *
     * @param algorithm algorithm details.
     * @param id algorithm id.
     * @return status response.
     */
    public CSResponse updateAlgorithm(Algorithm algorithm, int id) throws ValidationException {

        // Validate algorithm id.
        AlgorithmValidations.validateID(id);

        // Set algorithm id.
        algorithm.setId(id);

        // Validate algorithm name.
        AlgorithmValidations.validateName(algorithm.getName());

        // Update algorithm details.
        algorithmRepository.save(algorithm);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_UPDATE_SUCCESS);
    }


    /**
     * Delete algorithm.
     *
     * @param id algorithm id to delete.
     * @return status response.
     * @throws ValidationException for invalid algorithm id.
     */
    public CSResponse deleteAlgorithm(int id) throws ValidationException {

        // Validate algorithm id.
        AlgorithmValidations.validateID(id);

        // Validate algorithm id is already exists.
        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(id);
        if(!optionalAlgorithm.isPresent()) {
            throw new ValidationException(Strings.ALGORITHM_ID_INVALID);
        }

        // Delete algorithm.
        algorithmRepository.deleteById(id);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_DELETE_SUCCESS);
    }
}
