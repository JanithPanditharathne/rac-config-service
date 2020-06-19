package com.zone24x7.rac.configservice.recengine.bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.bundle.BundleDetail;
import com.zone24x7.rac.configservice.bundle.BundleList;
import com.zone24x7.rac.configservice.bundle.BundleService;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.RecEngine;
import com.zone24x7.rac.configservice.recengine.RecEngineRepository;
import com.zone24x7.rac.configservice.recengine.algorithm.RecEngineAlgorithm;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BUNDLES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

@Service
public class RecEngineBundleService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private BundleService bundleService;

    private static final int BUNDLE_CONFIG_ID = 1;

    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineBundleService.class);


    /**
     * Get bundle config json.
     *
     * @return Bundle config json
     */
    public String getBundleConfig() {
        return recEngineRepository.findByConfigType(BUNDLES).getConfigJson();
    }





    /**
     * Update bundle config json.
     *
     * @throws ServerException when bundle config update failed.
     */
    @Async("bundleTaskExecutor")
    public void updateBundleConfig() throws ServerException {

        // Get all bundles.
        BundleList allBundles = bundleService.getAllBundles();

        // Bundle list for rec engine.
        List<RecEngineBundle> bundleList = new ArrayList<>();

        // Update bundle details for each bundle.
        allBundles.getBundles().forEach(b -> {

            try {

                // Get bundle details.
                BundleDetail bundleDetail = bundleService.getBundle(b.getId());

                // Algorithm combine info.
                RecEngineBundleAlgorithmCombineInfo algorithmCombineInfo = new RecEngineBundleAlgorithmCombineInfo();
                algorithmCombineInfo.setEnableCombine(bundleDetail.isCombineEnabled());
                algorithmCombineInfo.setCombineDisplayText(bundleDetail.getCombineDisplayText());

                // Bundle algorithm list.
                List<RecEngineBundleAlgorithm> algorithms = new ArrayList<>();

                // Add details for each algorithm.
                bundleDetail.getAlgorithms().forEach(a ->
                        algorithms.add(new RecEngineBundleAlgorithm(
                                a.getRank(),
                                new RecEngineAlgorithm(a.getId(), a.getName(), "FLAT_ALGO", a.getDefaultDisplayText(), a.getCustomDisplayText())
                        ))
                );

                // Add rec engine bundle config to the list.
                bundleList.add(new RecEngineBundle(b.getId(), b.getName(), "FLAT", bundleDetail.getDefaultLimit(), algorithms, algorithmCombineInfo));

            } catch (ValidationException e) {
                LOGGER.info(e.getMessage(), e);
            }
        });

        try {

            // Get bundle config string.
            RecEngineBundleList recEngineBundleList = new RecEngineBundleList(bundleList);
            ObjectMapper objectMapper = new ObjectMapper();
            String bundleConfigString = objectMapper.writeValueAsString(recEngineBundleList);

            // Update bundle config.
            recEngineRepository.save(new RecEngine(BUNDLE_CONFIG_ID, BUNDLES, bundleConfigString));

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServerException(Strings.REC_ENGINE_BUNDLE_CONFIG_UPDATE_FAILED);
        }
    }






    /**
     * Add bundle config json.
     *
     * @param bundleConfig Bundle Json
     * @return Response
     */
    public CSResponse addBundleConfig(String bundleConfig) {
        recEngineRepository.save(new RecEngine(BUNDLE_CONFIG_ID, BUNDLES, bundleConfig));
        return new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");
    }

}
