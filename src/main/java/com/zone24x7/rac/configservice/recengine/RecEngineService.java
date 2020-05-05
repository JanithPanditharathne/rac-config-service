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
     * @param ruleJson Rule config json
     * @return  Response
     */
    CSResponse addRuleConfig(String ruleJson) {
        RecEngine recEngine = recEngineRepository.findByConfigType(RULES);
        recEngine.setConfigJson(ruleJson);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rule config json successfully added");
    }

    /**
     * Add rec Json.
     *
     * @param recJson Rec config json
     * @return Response
     */
    CSResponse addRecConfig(String recJson) {
        RecEngine recEngine = recEngineRepository.findByConfigType(RECS);
        recEngine.setConfigJson(recJson);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rec config json successfully added");
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotJson Rec slot config json
     * @return  Response
     */
    CSResponse addRecSlotConfig(String recSlotJson) {
        RecEngine recEngine = recEngineRepository.findByConfigType(REC_SLOTS);
        recEngine.setConfigJson(recSlotJson);
        recEngineRepository.save(recEngine);
        return new CSResponse(SUCCESS,"CS-0000: Rec slot config json successfully added");
    }
}
