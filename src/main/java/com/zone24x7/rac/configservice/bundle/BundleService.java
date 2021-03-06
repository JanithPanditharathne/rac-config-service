package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.algorithm.AlgorithmValidations;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.rec.Rec;
import com.zone24x7.rac.configservice.rec.RecRepository;
import com.zone24x7.rac.configservice.recengine.RecEngineService;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zone24x7.rac.configservice.util.Strings.ALGORITHM_ID_DOES_NOT_EXIST;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_ADDED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_DELETED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_ID_INVALID;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_UPDATED_SUCCESSFULLY;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class BundleService {

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleAlgorithmRepository bundleAlgorithmRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private RecRepository recRepository;

    @Autowired
    @Lazy
    private RecEngineService recEngineService;


    /**
     * Get all bundles.
     *
     * @return All bundles
     */
    public BundleList getAllBundles() {
        return new BundleList(bundleRepository.findAllByOrderByIdDesc());
    }

    /**
     * Get bundle details by ID.
     *
     * @param bundleID Bundle ID
     * @return         Bundle details
     * @throws ValidationException Exception to throw
     */
    public BundleDetail getBundle(int bundleID) throws ValidationException {

        // Validate bundle id.
        BundleValidations.validateID(bundleID);

        // Find given bundle id from db.
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

        // Validate bundle detail.
        validateBundleDetail(bundleDetail);

        // Save new bundle.
        Bundle bundle = bundleRepository.save(new Bundle(bundleDetail.getName(), bundleDetail.getDefaultLimit(),
                bundleDetail.isCombineEnabled(), bundleDetail.getCombineDisplayText()));


        // Save all new bundle-algorithm associations.
        List<BundleAlgorithm> bundleAlgorithms = new ArrayList<>();
        bundleDetail.getAlgorithms().forEach(a -> bundleAlgorithms.add(new BundleAlgorithm(bundle.getId(), a.getId(),
                a.getCustomDisplayText(), a.getRank())));
        bundleAlgorithmRepository.saveAll(bundleAlgorithms);

        // Update rec engine bundle config.
        recEngineService.updateBundleConfig();

        // Return status.
        String[] arr = BUNDLE_ADDED_SUCCESSFULLY.split(":");
        return new CSResponse(SUCCESS, arr[0], arr[1], String.valueOf(bundle.getId()));
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

        // Validate bundle id.
        BundleValidations.validateID(bundleID);

        // Retrieve bundle for the given id.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // Check bundle id is exists.
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Validate other bundle detail.
        validateBundleDetail(bundleDetail);


        // Update bundle details in db.
        Bundle bundle = new Bundle(bundleDetail.getName(), bundleDetail.getDefaultLimit(), bundleDetail.isCombineEnabled(), bundleDetail.getCombineDisplayText());
        bundle.setId(bundleID);
        bundleRepository.save(bundle);


        // Find all existing bundle-algorithm associations for given bundle id and delete them.
        List<BundleAlgorithm> existingBundleAlgorithms = bundleAlgorithmRepository.findAllByBundleID(bundleID);
        bundleAlgorithmRepository.deleteAll(existingBundleAlgorithms);

        // Save all new bundle-algorithm associations.
        List<BundleAlgorithm> newBundleAlgorithms = new ArrayList<>();
        bundleDetail.getAlgorithms().forEach(a -> newBundleAlgorithms.add(new BundleAlgorithm(bundleID, a.getId(), a.getCustomDisplayText(), a.getRank())));
        bundleAlgorithmRepository.saveAll(newBundleAlgorithms);


        // Update rec engine bundle config.
        recEngineService.updateBundleConfig();

        // Return status.
        return new CSResponse(SUCCESS, BUNDLE_UPDATED_SUCCESSFULLY);
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

        // Validate bundle id.
        BundleValidations.validateID(id);

        // Check given bundle id is exists.
        Optional<Bundle> bundleOptional = bundleRepository.findById(id);
        if (!bundleOptional.isPresent()) {
            throw new ValidationException(BUNDLE_ID_INVALID);
        }

        // Check bundle id already use in recs.
        List<Rec> recs = recRepository.findAllByBundleID(id);
        if (!recs.isEmpty()) {
            throw new ValidationException(Strings.BUNDLE_ID_ALREADY_USE_IN_RECS);
        }


        // Get all bundle-algorithms associations for given bundle id.
        List<BundleAlgorithm> allBundleAlgorithms = bundleAlgorithmRepository.findAllByBundleID(id);

        // Delete bundle-algorithm associations.
        allBundleAlgorithms.forEach(bundleAlgorithm -> bundleAlgorithmRepository.delete(bundleAlgorithm));


        // Delete bundle.
        Bundle bundle = bundleOptional.get();
        bundleRepository.delete(bundle);

        // Update rec engine bundle config.
        recEngineService.updateBundleConfig();

        // Return status.
        return new CSResponse(SUCCESS, BUNDLE_DELETED_SUCCESSFULLY);
    }






    /**
     * Validate bundle details.
     *
     * @param bundleDetail bundle detail.
     * @throws ValidationException if validation failed.
     */
    private void validateBundleDetail(BundleDetail bundleDetail) throws ValidationException {

        // Validate bundle name.
        BundleValidations.validateName(bundleDetail.getName());

        // Validate default limit.
        BundleValidations.validateDefaultLimit(bundleDetail.getDefaultLimit());

        // Validate combine display text when combine enabled.
        if (bundleDetail.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetail.getCombineDisplayText());
        }

        // Get algorithms.
        List<BundleAlgorithmDetail> algorithms = bundleDetail.getAlgorithms();

        // Validate algorithms array.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate each algorithms are valid.
        for (BundleAlgorithmDetail bundleAlgorithmDetail : algorithms) {

            // Check algorithm id.
            AlgorithmValidations.validateID(bundleAlgorithmDetail.getId());

            // Check algorithm id is exists.
            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDetail.getId());
            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_ID_DOES_NOT_EXIST + " (" + bundleAlgorithmDetail.getId() + ")");
            }
        }
    }
}
