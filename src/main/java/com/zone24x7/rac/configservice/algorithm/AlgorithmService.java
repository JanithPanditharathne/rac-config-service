package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.bundle.BundleAlgorithm;
import com.zone24x7.rac.configservice.bundle.BundleAlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlgorithmService {

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private BundleAlgorithmRepository bundleAlgorithmRepository;


    /**
     * Return algorithm list.
     *
     * @return algorithm list.
     */
    public AlgorithmList getAllAlgorithms() {
        List<Algorithm> algorithms = new ArrayList<>(algorithmRepository.findAll());
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
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_ADDED_SUCCESSFULLY);

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

        // Validate algorithm id is already exists.
        Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(id);
        if(!optionalAlgorithm.isPresent()) {
            throw new ValidationException(Strings.ALGORITHM_ID_INVALID);
        }

        // Set algorithm id.
        algorithm.setId(id);

        // Validate algorithm name.
        AlgorithmValidations.validateName(algorithm.getName());

        // Update algorithm details.
        algorithmRepository.save(algorithm);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_UPDATED_SUCCESSFULLY);
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

        // Check algorithm id is in use.
        List<BundleAlgorithm> bundleAlgorithms = bundleAlgorithmRepository.findAllByAlgorithmID(id);
        if(!bundleAlgorithms.isEmpty()) {
            throw new ValidationException(Strings.ALGORITHM_ID_ALREADY_USE_IN_BUNDLES);
        }

        // Delete algorithm.
        algorithmRepository.deleteById(id);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.ALGORITHM_DELETED_SUCCESSFULLY);
    }
}
