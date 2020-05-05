package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zone24x7.rac.configservice.util.Strings.*;

/**
 * Service class relating to Rec Engine.
 *
 */
@Service
public class RecEngineService {

    private RecEngineRepository recEngineRepository;

    @Autowired
    public RecEngineService(RecEngineRepository recEngineRepository) {
        this.recEngineRepository = recEngineRepository;
    }

    /**
     * Add bundle Json.
     *
     * @param bundleJson Bundle Json
     * @return           Message DTO
     */
    Response addBundleJson(String bundleJson) {

        RecEngine recEngine = recEngineRepository.findByConfigType(BUNDLES);
        recEngine.setConfigJson(bundleJson);

        recEngineRepository.save(recEngine);

        return new Response(SUCCESS,"N/A", "Bundle JSON successfully added");
    }

    /**
     * Add rule Json.
     *
     * @param ruleJson Rule Json
     * @return         Message DTO
     */
    Response addRuleJson(String ruleJson) {

        RecEngine recEngine = recEngineRepository.findByConfigType(RULES);
        recEngine.setConfigJson(ruleJson);

        recEngineRepository.save(recEngine);

        return new Response(SUCCESS,"N/A", "Rule JSON successfully added");
    }

    /**
     * Add rec Json.
     *
     * @param recJson Rec Json
     * @return        Message DTO
     */
    Response addRecJson(String recJson) {

        RecEngine recEngine = recEngineRepository.findByConfigType(RECS);
        recEngine.setConfigJson(recJson);

        recEngineRepository.save(recEngine);

        return new Response(SUCCESS,"N/A", "Recommendation JSON successfully added");
    }

    /**
     * Add rec slot Json.
     *
     * @param recSlotJson Rec slot Json
     * @return            Message DTO
     */
    Response addRecSlotJson(String recSlotJson) {

        RecEngine recEngine = recEngineRepository.findByConfigType(REC_SLOTS);
        recEngine.setConfigJson(recSlotJson);

        recEngineRepository.save(recEngine);

        return new Response(SUCCESS,"N/A", "Recommendation slots JSON successfully added");
    }
}
