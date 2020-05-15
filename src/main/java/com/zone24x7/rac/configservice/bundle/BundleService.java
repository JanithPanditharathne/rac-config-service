package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.algorithm.Algorithm;
import com.zone24x7.rac.configservice.algorithm.AlgorithmRepository;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    BundleSummaryListDTO getAllBundles() {

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
    BundleDetailDTO getBundle(int bundleID) throws ValidationException {

        logger.info("Finding bundle ID from database");

        // Find given bundle ID from DB.
        Optional<Bundle> bundleOptional = bundleRepository.findById(bundleID);

        // If bundle ID found, return values.
        if (bundleOptional.isPresent()) {

            logger.info("Bundle ID found");

            // Retrieve bundle.
            Bundle bundle = bundleOptional.get();

            BundleDetailDTO bundleDetailDTO = new BundleDetailDTO();
            bundleDetailDTO.setId(bundleID);
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
            throw new ValidationException(Strings.BUNDLE_ID_INVALID);
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
}
