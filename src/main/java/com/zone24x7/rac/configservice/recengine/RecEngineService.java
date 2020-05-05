package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * Add bundle config json.
     *
     * @param bundleConfig Bundle Json
     * @return Response
     */
    CSResponse addBundleConfig(String bundleConfig) {
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
    CSResponse addRuleConfig(String ruleConfig) {
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
    CSResponse addRecConfig(String recConfig) {
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
    CSResponse addRecSlotConfig(String recSlotConfig) {
        RecEngine recEngine = recEngineRepository.findByConfigType(REC_SLOTS);
        recEngine.setConfigJson(recSlotConfig);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");
    }

    /**
     * Get bundle config json.
     *
     * @return Bundle config json
     */
    String getBundleConfig() {
        return recEngineRepository.findByConfigType(BUNDLES).getConfigJson();
    }

    /**
     * Get rule config json.
     *
     * @return Rule config json
     */
    String getRuleConfig() {
        return recEngineRepository.findByConfigType(RULES).getConfigJson();
    }

    /**
     * Get recs config json.
     *
     * @return Recs config json
     */
    String getRecsConfig() {
        return recEngineRepository.findByConfigType(RECS).getConfigJson();
    }

    /**
     * Get rec slots config json.
     *
     * @return Rec slots config json
     */
    String getRecSlotsConfig() {
        return recEngineRepository.findByConfigType(REC_SLOTS).getConfigJson();
    }
}
