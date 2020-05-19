package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.*;

@Service
public class BundleService {

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleAlgorithmRepository bundleAlgorithmRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private RecEngineService recEngineService;

    // Logger.
    Logger logger = LoggerFactory.getLogger(BundleService.class);

    private static final String BUNDLE_CONFIG_UPDATE = "Updating bundles for rec engine";

    /**
     * Get all bundles.
     *
     * @return All bundles
     */
    public BundleList getAllBundles() {
        logger.info("Retrieving all bundles");

        // Retrieve all bundles.
        return new BundleList(bundleRepository.findAll());
    }

    /**
     * Get bundle details by ID.
     *
     * @param bundleID Bundle ID
     * @return         Bundle details
     * @throws ValidationException Exception to throw
     */
    public BundleDetails getBundle(int bundleID) throws ValidationException {

        logger.info("Finding bundle ID from database");

        // Find given bundle ID from DB.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // If bundle ID found, return values.
        if (bundleOptional.isPresent()) {

            logger.info("Bundle ID found");

            // Retrieve bundle.
            Bundle bundle = bundleOptional.get();

            BundleDetails bundleDetails = new BundleDetails();
            bundleDetails.setId(bundleID);
            bundleDetails.setName(bundle.getName());
            bundleDetails.setDefaultLimit(bundle.getDefaultLimit());
            bundleDetails.setCombineEnabled(bundle.isCombineEnabled());
            bundleDetails.setCombineDisplayText(bundle.getCombineDisplayText());
            bundleDetails.setAlgorithms(getAssignedAlgorithms(bundleID));

            return bundleDetails;

        } else {

            logger.info("Bundle ID not found");

            // Bundle not found in db.
            // Return invalid bundle ID error.
            throw new ValidationException(BUNDLE_ID_INVALID);
        }
    }

    /**
     * Get algorithms assigned to bundles.
     *
     * @param bundleID Bundle ID
     * @return         Assigned algorithms
     */
    private List<BundleAlgorithmDetails> getAssignedAlgorithms(int bundleID) {
        List<BundleAlgorithmDetails> bundleAlgorithmDetailsList = new ArrayList<>();

        logger.info("Retrieve algorithms associated to bundle ID");

        // Retrieve algorithms associated to given bundle
        List<BundleAlgorithm> bundleAlgorithmList = bundleAlgorithmRepository.findAllByBundleID(bundleID);

        // Associated algorithms exist.
        if (!bundleAlgorithmList.isEmpty()) {

            // Iterate through list.
            for (BundleAlgorithm bundleAlgorithm : bundleAlgorithmList) {

                int algorithmID = bundleAlgorithm.getAlgorithmID();

                logger.info("Retrieve algorithms details");

                // Retrieve algorithm details.
                Optional<Algorithm> algorithmOptional = algorithmRepository.findById(algorithmID);

                Algorithm algorithm = algorithmOptional.get();

                BundleAlgorithmDetails bundleAlgorithmDetails = new BundleAlgorithmDetails();
                bundleAlgorithmDetails.setId(algorithmID);
                bundleAlgorithmDetails.setName(algorithm.getName());
                bundleAlgorithmDetails.setRank(bundleAlgorithm.getRank());
                bundleAlgorithmDetails.setDefaultDisplayText(algorithm.getDefaultDisplayText());
                bundleAlgorithmDetails.setCustomDisplayText(bundleAlgorithm.getCustomDisplayText());

                // Add to list
                bundleAlgorithmDetailsList.add(bundleAlgorithmDetails);
            }
        }

        return bundleAlgorithmDetailsList;
    }

    /**
     * Add new bundle.
     *
     * @param bundleDetails Bundle details to add
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addBundle(BundleDetails bundleDetails) throws ValidationException, ServerException {

        // Validate bundle name.
        BundleValidations.validateName(bundleDetails.getName());

        // Validate combine display text when combine enabled.
        if (bundleDetails.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetails.getCombineDisplayText());
        }

        // Get algorithms.
        List<BundleAlgorithmDetails> algorithms = bundleDetails.getAlgorithms();

        // Validate algorithms.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate whether algorithms are valid.
        for (BundleAlgorithmDetails bundleAlgorithmDetails : algorithms) {

            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDetails.getId());

            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_DOES_NOT_EXIST + " (" + bundleAlgorithmDetails.getId() + ")");
            }
        }

        Bundle bundle = new Bundle();
        bundle.setName(bundleDetails.getName());
        bundle.setDefaultLimit(bundleDetails.getDefaultLimit());
        bundle.setCombineEnabled(bundleDetails.isCombineEnabled());
        bundle.setCombineDisplayText(bundleDetails.getCombineDisplayText());

        // Save bundle.
        bundleRepository.save(bundle);
        logger.info("Bundle saved to db");

        // Iterate through algorithms.
        saveBundleAlgorithms(algorithms, bundle.getId());

        logger.info("Bundle - algorithm associations saved to db");

        logger.info(BUNDLE_CONFIG_UPDATE);

        // Update rec engine bundle list.
        recEngineService.updateBundleConfig();

        return new CSResponse(SUCCESS, BUNDLE_ADD_SUCCESS);
    }

    /**
     * Edit bundle.
     *
     * @param bundleID      Bundle ID
     * @param bundleDetails Bundle details to edit
     * @return              CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Server exception to throw
     */
    public CSResponse editBundle(int bundleID, BundleDetails bundleDetails) throws ValidationException, ServerException {

        // Retrieve bundle for the given ID.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // Validate bundle ID.
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Set bundle ID.
        bundleDetails.setId(bundleID);

        // Validate bundle name.
        BundleValidations.validateName(bundleDetails.getName());

        // Validate combine display text when combine enabled.
        if (bundleDetails.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetails.getCombineDisplayText());
        }

        // Get algorithms.
        List<BundleAlgorithmDetails> algorithms = bundleDetails.getAlgorithms();

        // Validate algorithms.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate whether algorithms are valid.
        for (BundleAlgorithmDetails bundleAlgorithmDetails : algorithms) {

            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDetails.getId());

            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_DOES_NOT_EXIST + " (" + bundleAlgorithmDetails.getId() + ")");
            }
        }

        Bundle bundle = bundleOptional.get();
        bundle.setName(bundleDetails.getName());
        bundle.setDefaultLimit(bundleDetails.getDefaultLimit());
        bundle.setCombineEnabled(bundleDetails.isCombineEnabled());
        bundle.setCombineDisplayText(bundleDetails.getCombineDisplayText());

        // Save bundle.
        bundleRepository.save(bundle);
        logger.info("Bundle saved to db");

        // Get all bundle - algorithm associations.
        List<BundleAlgorithm> allBundleAlgorithms = bundleAlgorithmRepository.findAllByBundleID(bundleID);

        // Iterate through list.
        for (BundleAlgorithm bundleAlgorithm : allBundleAlgorithms) {

            // Delete association from DB.
            bundleAlgorithmRepository.delete(bundleAlgorithm);
        }

        logger.info("Existing bundle - algorithm associations removed from db");

        // Iterate through algorithms.
        saveBundleAlgorithms(algorithms, bundleID);

        logger.info("New bundle - algorithm associations saved to db");

        logger.info(BUNDLE_CONFIG_UPDATE);

        // Update rec engine bundle list.
        recEngineService.updateBundleConfig();

        return new CSResponse(SUCCESS, BUNDLE_UPDATE_SUCCESS);
    }

    /**
     * Save bundle - algorithm associations.
     *
     * @param algorithms Algorithms
     * @param bundleID   Bundle ID
     */
    private void saveBundleAlgorithms(List<BundleAlgorithmDetails> algorithms, int bundleID) {

        for (BundleAlgorithmDetails bundleAlgorithmDetails : algorithms) {

            BundleAlgorithm bundleAlgorithm = new BundleAlgorithm();
            bundleAlgorithm.setBundleID(bundleID);
            bundleAlgorithm.setAlgorithmID(bundleAlgorithmDetails.getId());
            bundleAlgorithm.setCustomDisplayText(bundleAlgorithmDetails.getCustomDisplayText());
            bundleAlgorithm.setRank(bundleAlgorithmDetails.getRank());

            // Save bundle - algorithm associations.
            bundleAlgorithmRepository.save(bundleAlgorithm);
        }
    }

    /**
     * Delete bundle for given id.
     *
     * @param id Bundle ID
     * @return   CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Server exception to throw
     */
    public CSResponse deleteBundle(int id) throws ValidationException, ServerException {

        // Retrieve bundle for the given ID.
        Optional<Bundle> bundleOptional = bundleRepository.findById(id);

        // Validate bundle ID.
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Get all bundle - algorithms associations by bundle ID.
        List<BundleAlgorithm> allBundleAlgorithms = bundleAlgorithmRepository.findAllByBundleID(id);

        // Delete bundle - algorithm associations
        allBundleAlgorithms.forEach(bundleAlgorithm -> bundleAlgorithmRepository.delete(bundleAlgorithm));

        logger.info("Deleted all bundle - algorithm associations");

        // Retrieve bundle.
        Bundle bundle = bundleOptional.get();

        // Remove bundle from DB.
        bundleRepository.delete(bundle);

        logger.info("Deleted bundle");

        logger.info(BUNDLE_CONFIG_UPDATE);

        // Update rec engine bundle list.
        recEngineService.updateBundleConfig();

        return new CSResponse(SUCCESS, BUNDLE_DELETE_SUCCESS);
    }
}
