package com.zone24x7.rac.configservice.bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BundleService {

    @Autowired
    private BundleRepository bundleRepository;

    // Logger.
    Logger logger = LoggerFactory.getLogger(BundleService.class);

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
}
