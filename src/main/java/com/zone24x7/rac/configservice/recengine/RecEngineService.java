package com.zone24x7.rac.configservice.recengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.bundle.BundleDetailDTO;
import com.zone24x7.rac.configservice.bundle.BundleService;
import com.zone24x7.rac.configservice.bundle.BundleSummaryListDTO;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundle;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithm;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithmCombineInfo;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleAlgorithmList;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleList;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BUNDLES;
import static com.zone24x7.rac.configservice.util.Strings.RECS;
import static com.zone24x7.rac.configservice.util.Strings.REC_SLOTS;
import static com.zone24x7.rac.configservice.util.Strings.RULES;
import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

/**
 * Service class relating to Rec Engine.
 *
 */
@Service
public class RecEngineService {

    @Autowired
    private RecEngineRepository recEngineRepository;

    @Autowired
    private BundleService bundleService;


    // Logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(RecEngineService.class);



    /**
     * Get bundle config json.
     *
     * @return Bundle config json
     */
    public String getBundleConfig() {
        return recEngineRepository.findByConfigType(BUNDLES).getConfigJson();
    }

    /**
     * Get rule config json.
     *
     * @return Rule config json
     */
    public String getRuleConfig() {
        return recEngineRepository.findByConfigType(RULES).getConfigJson();
    }

    /**
     * Get recs config json.
     *
     * @return Recs config json
     */
    public String getRecsConfig() {
        return recEngineRepository.findByConfigType(RECS).getConfigJson();
    }

    /**
     * Get rec slots config json.
     *
     * @return Rec slots config json
     */
    public String getRecSlotsConfig() {
        return recEngineRepository.findByConfigType(REC_SLOTS).getConfigJson();
    }


    /**
     * Update bundle config json.
     *
     * @throws ServerException when bundle config update failed.
     */
    public void updateBundleConfig() throws ServerException {

        // Get all bundles.
        BundleSummaryListDTO allBundles = bundleService.getAllBundles();

        // Bundle list for rec engine.
        List<RecEngineBundle> bundleList = new ArrayList<>();

        // Update bundle details for each bundle.
        allBundles.getBundles().forEach(b -> {

            try {

                // Get bundle details.
                BundleDetailDTO bundleDetail = bundleService.getBundle(b.getId());

                // Algorithm combine info.
                RecEngineBundleAlgorithmCombineInfo algorithmCombineInfo = new RecEngineBundleAlgorithmCombineInfo();
                algorithmCombineInfo.setEnableCombine(bundleDetail.isCombineEnabled());
                algorithmCombineInfo.setCombineDisplayText(bundleDetail.getCombineDisplayText());

                // Bundle algorithm list.
                List<RecEngineBundleAlgorithmList> algorithms = new ArrayList<>();

                // Add details for each algorithm.
                bundleDetail.getAlgorithms().forEach(a -> {
                    algorithms.add(new RecEngineBundleAlgorithmList(
                            a.getRank(),
                            new RecEngineBundleAlgorithm(a.getId(), a.getName(), "FLAT_ALGO", a.getDefaultDisplayText(), a.getCustomDisplayText())
                    ));

                });

                // Add rec engine bundle config to the list.
                bundleList.add(new RecEngineBundle(b.getId(), b.getName(), null, bundleDetail.getDefaultLimit(), algorithms, algorithmCombineInfo));


            } catch (ValidationException e) {
                LOGGER.info(e.getMessage(), e);
            }


        });


        try {

            RecEngineBundleList recEngineBundleList = new RecEngineBundleList(bundleList);
            ObjectMapper objectMapper = new ObjectMapper();
            String bundleConfigString = objectMapper.writeValueAsString(recEngineBundleList);

            RecEngine recEngine = recEngineRepository.findByConfigType(BUNDLES);
            recEngine.setConfigJson(bundleConfigString);
            recEngineRepository.save(recEngine);


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
        RecEngine recEngine = recEngineRepository.findByConfigType(BUNDLES);
        recEngine.setConfigJson(bundleConfig);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Bundle config json successfully added");
    }

    /**
     * Add rule config json.
     *
     * @param ruleConfig Rule config json
     * @return  Response
     */
    public CSResponse addRuleConfig(String ruleConfig) {
        RecEngine recEngine = recEngineRepository.findByConfigType(RULES);
        recEngine.setConfigJson(ruleConfig);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");
    }

    /**
     * Add rec Json.
     *
     * @param recConfig Rec config json
     * @return Response
     */
    public CSResponse addRecConfig(String recConfig) {
        RecEngine recEngine = recEngineRepository.findByConfigType(RECS);
        recEngine.setConfigJson(recConfig);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rec config json successfully added");
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotConfig Rec slot config json
     * @return  Response
     */
    public CSResponse addRecSlotConfig(String recSlotConfig) {
        RecEngine recEngine = recEngineRepository.findByConfigType(REC_SLOTS);
        recEngine.setConfigJson(recSlotConfig);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");
    }


}
