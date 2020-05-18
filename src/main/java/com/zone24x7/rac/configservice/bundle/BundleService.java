package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ValidationException;
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

    // Logger.
    Logger logger = LoggerFactory.getLogger(BundleService.class);

    /**
     * Get all bundles.
     *
     * @return All bundles
     */
    public BundleSummaryListDTO getAllBundles() {

        logger.info("Retrieving all bundles");

        // Retrieve all bundles.
        List<Bundle> allBundles = bundleRepository.findAll();

        List<BundleSummaryDTO> bundleSummaryDTOList = new ArrayList<>();

        // Iterate bundle list.
        for (Bundle bundle : allBundles) {
            BundleSummaryDTO bundleSummaryDTO = new BundleSummaryDTO();
            bundleSummaryDTO.setId(bundle.getId());
            bundleSummaryDTO.setName(bundle.getName());

            // Add to list.
            bundleSummaryDTOList.add(bundleSummaryDTO);
        }

        return new BundleSummaryListDTO(bundleSummaryDTOList);
    }

    /**
     * Get bundle details by ID.
     *
     * @param bundleID Bundle ID
     * @return         Bundle details
     * @throws ValidationException Exception to throw
     */
    public BundleDetailDTO getBundle(int bundleID) throws ValidationException {

        logger.info("Finding bundle ID from database");

        // Find given bundle ID from DB.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // If bundle ID found, return values.
        if (bundleOptional.isPresent()) {

            logger.info("Bundle ID found");

            // Retrieve bundle.
            Bundle bundle = bundleOptional.get();

            BundleDetailDTO bundleDetailDTO = new BundleDetailDTO();
            bundleDetailDTO.setId(String.valueOf(bundleID));
            bundleDetailDTO.setName(bundle.getName());
            bundleDetailDTO.setDefaultLimit(bundle.getDefaultLimit());
            bundleDetailDTO.setCombineEnabled(bundle.isCombineEnabled());
            bundleDetailDTO.setCombineDisplayText(bundle.getCombineDisplayText());
            bundleDetailDTO.setAlgorithms(getAssignedAlgorithms(bundleID));

            return  bundleDetailDTO;

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
    private List<BundleAlgorithmDTO> getAssignedAlgorithms(int bundleID) {
        List<BundleAlgorithmDTO> bundleAlgorithmDTOList = new ArrayList<>();

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

                BundleAlgorithmDTO bundleAlgorithmDTO = new BundleAlgorithmDTO();
                bundleAlgorithmDTO.setId(algorithmID);
                bundleAlgorithmDTO.setName(algorithm.getName());
                bundleAlgorithmDTO.setRank(bundleAlgorithm.getRank());
                bundleAlgorithmDTO.setDefaultDisplayText(algorithm.getDefaultDisplayText());
                bundleAlgorithmDTO.setCustomDisplayText(bundleAlgorithm.getCustomDisplayText());

                // Add to list
                bundleAlgorithmDTOList.add(bundleAlgorithmDTO);
            }
        }

        return bundleAlgorithmDTOList;
    }

    CSResponse addBundle(BundleDetailDTO bundleDetailDTO) throws ValidationException {

        // Validate bundle name.
        BundleValidations.validateName(bundleDetailDTO.getName());

        // Validate combine display text when combine enabled.
        if (bundleDetailDTO.isCombineEnabled()) {

            // Validate combined display text.
            BundleValidations.validateCombinedDisplayText(bundleDetailDTO.getCombineDisplayText());
        }

        List<BundleAlgorithmDTO> algorithms = bundleDetailDTO.getAlgorithms();

        // Validate algorithms.
        BundleValidations.validateAlgorithms(algorithms);

        // Validate whether algorithms are valid.
        for (BundleAlgorithmDTO bundleAlgorithmDTO : algorithms) {

            Optional<Algorithm> algorithmOptional = algorithmRepository.findById(bundleAlgorithmDTO.getId());

            if (!algorithmOptional.isPresent()) {
                throw new ValidationException(ALGORITHM_DOES_NOT_EXIST + " (" + bundleAlgorithmDTO.getId() + ")");
            }
        }

        Bundle bundle = new Bundle();
        bundle.setName(bundleDetailDTO.getName());
        bundle.setDefaultLimit(bundleDetailDTO.getDefaultLimit());
        bundle.setCombineEnabled(bundleDetailDTO.isCombineEnabled());
        bundle.setCombineDisplayText(bundleDetailDTO.getCombineDisplayText());

        // Save bundle.
        bundleRepository.save(bundle);
        logger.info("Bundle saved to db");

        // Iterate through algorithms.
        for (BundleAlgorithmDTO bundleAlgorithmDTO : algorithms) {

            BundleAlgorithm bundleAlgorithm = new BundleAlgorithm();
            bundleAlgorithm.setBundleID(bundle.getId());
            bundleAlgorithm.setAlgorithmID(bundleAlgorithmDTO.getId());
            bundleAlgorithm.setCustomDisplayText(bundleAlgorithmDTO.getCustomDisplayText());
            bundleAlgorithm.setRank(bundleAlgorithmDTO.getRank());

            // Save bundle - algorithm associations.
            bundleAlgorithmRepository.save(bundleAlgorithm);
        }

        logger.info("Bundle - algorithm associations saved to db");

        return new CSResponse(SUCCESS, BUNDLE_ADD_SUCCESS);
    }
}
