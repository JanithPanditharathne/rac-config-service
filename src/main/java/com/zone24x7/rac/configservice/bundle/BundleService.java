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
    private static final Logger LOGGER = LoggerFactory.getLogger(BundleService.class);

    private static final String BUNDLE_CONFIG_UPDATE = "Updating bundles for rec engine";

    /**
     * Get all bundles.
     *
     * @return All bundles
     */
    public BundleList getAllBundles() {
        return new BundleList(bundleRepository.findAll());
    }

    /**
     * Get bundle details by ID.
     *
     * @param bundleID Bundle ID
     * @return         Bundle details
     * @throws ValidationException Exception to throw
     */
    public BundleDetail getBundle(int bundleID) throws ValidationException {

        // Find given bundle ID from DB.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // If bundle not found in db, return invalid bundle id error.
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Algorithm details list.
        List<BundleAlgorithmDetail> algorithmDetails = new ArrayList<>();

        // Retrieve algorithms associated to given bundle.
        List<BundleAlgorithm> bundleAlgorithmList = bundleAlgorithmRepository.findAllByBundleID(bundleID);

        // Set algorithm details for each algorithm.
        bundleAlgorithmList.forEach(a -> {

            // Retrieve additional details from algorithm table.
            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(a.getAlgorithmID());
            if(algorithmOptional.isPresent()) {
                Algorithm algorithm = algorithmOptional.get();
                algorithmDetails.add(new BundleAlgorithmDetail(a.getAlgorithmID(), algorithm.getName(), a.getRank(),
                        algorithm.getDefaultDisplayText(), a.getCustomDisplayText()));
            }

        });


        // Get bundle.
        Bundle bundle = bundleOptional.get();

        // Return bundle details.
        return new BundleDetail(bundleID, bundle.getName(), bundle.getDefaultLimit(), bundle.isCombineEnabled(),
                bundle.getCombineDisplayText(), algorithmDetails);
    }


    /**
     * Add new bundle.
     *
     * @param bundleDetail Bundle details to add
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    public CSResponse addBundle(BundleDetail bundleDetail) throws ValidationException, ServerException {

        // Validate bundle name.
        BundleValidations.validateName(bundleDetail.getName());

        // Validate combine display text when combine enabled.
        if (bundleDetail.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetail.getCombineDisplayText());
        }

        // Get algorithms.
        List<BundleAlgorithmDetail> algorithms = bundleDetail.getAlgorithms();

        // Validate algorithms.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate whether algorithms are valid.
        for (BundleAlgorithmDetail bundleAlgorithmDetail : algorithms) {

            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDetail.getId());

            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_DOES_NOT_EXIST + " (" + bundleAlgorithmDetail.getId() + ")");
            }
        }

        Bundle bundle = new Bundle();
        bundle.setName(bundleDetail.getName());
        bundle.setDefaultLimit(bundleDetail.getDefaultLimit());
        bundle.setCombineEnabled(bundleDetail.isCombineEnabled());
        bundle.setCombineDisplayText(bundleDetail.getCombineDisplayText());

        // Save bundle.
        bundleRepository.save(bundle);
        LOGGER.info("Bundle saved to db");

        // Iterate through algorithms.
        saveBundleAlgorithms(algorithms, bundle.getId());

        LOGGER.info("Bundle - algorithm associations saved to db");

        LOGGER.info(BUNDLE_CONFIG_UPDATE);

        // Update rec engine bundle list.
        recEngineService.updateBundleConfig();

        return new CSResponse(SUCCESS, BUNDLE_ADD_SUCCESS);
    }

    /**
     * Edit bundle.
     *
     * @param bundleID      Bundle ID
     * @param bundleDetail Bundle details to edit
     * @return              CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Server exception to throw
     */
    public CSResponse editBundle(int bundleID, BundleDetail bundleDetail) throws ValidationException, ServerException {

        // Retrieve bundle for the given ID.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // Validate bundle ID.
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Set bundle ID.
        bundleDetail.setId(bundleID);

        // Validate bundle name.
        BundleValidations.validateName(bundleDetail.getName());

        // Validate combine display text when combine enabled.
        if (bundleDetail.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetail.getCombineDisplayText());
        }

        // Get algorithms.
        List<BundleAlgorithmDetail> algorithms = bundleDetail.getAlgorithms();

        // Validate algorithms.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate whether algorithms are valid.
        for (BundleAlgorithmDetail bundleAlgorithmDetail : algorithms) {

            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDetail.getId());

            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_DOES_NOT_EXIST + " (" + bundleAlgorithmDetail.getId() + ")");
            }
        }

        Bundle bundle = bundleOptional.get();
        bundle.setName(bundleDetail.getName());
        bundle.setDefaultLimit(bundleDetail.getDefaultLimit());
        bundle.setCombineEnabled(bundleDetail.isCombineEnabled());
        bundle.setCombineDisplayText(bundleDetail.getCombineDisplayText());

        // Save bundle.
        bundleRepository.save(bundle);
        LOGGER.info("Bundle saved to db");

        // Get all bundle - algorithm associations.
        List<BundleAlgorithm> allBundleAlgorithms = bundleAlgorithmRepository.findAllByBundleID(bundleID);

        // Iterate through list.
        for (BundleAlgorithm bundleAlgorithm : allBundleAlgorithms) {

            // Delete association from DB.
            bundleAlgorithmRepository.delete(bundleAlgorithm);
        }

        LOGGER.info("Existing bundle - algorithm associations removed from db");

        // Iterate through algorithms.
        saveBundleAlgorithms(algorithms, bundleID);

        LOGGER.info("New bundle - algorithm associations saved to db");

        LOGGER.info(BUNDLE_CONFIG_UPDATE);

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
    private void saveBundleAlgorithms(List<BundleAlgorithmDetail> algorithms, int bundleID) {

        for (BundleAlgorithmDetail bundleAlgorithmDetail : algorithms) {

            BundleAlgorithm bundleAlgorithm = new BundleAlgorithm();
            bundleAlgorithm.setBundleID(bundleID);
            bundleAlgorithm.setAlgorithmID(bundleAlgorithmDetail.getId());
            bundleAlgorithm.setCustomDisplayText(bundleAlgorithmDetail.getCustomDisplayText());
            bundleAlgorithm.setRank(bundleAlgorithmDetail.getRank());

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

        LOGGER.info("Deleted all bundle - algorithm associations");

        // Retrieve bundle.
        Bundle bundle = bundleOptional.get();

        // Remove bundle from DB.
        bundleRepository.delete(bundle);

        LOGGER.info("Deleted bundle");

        LOGGER.info(BUNDLE_CONFIG_UPDATE);

        // Update rec engine bundle list.
        recEngineService.updateBundleConfig();

        return new CSResponse(SUCCESS, BUNDLE_DELETE_SUCCESS);
    }
}
