package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.recengine.bundle.RecEngineBundleService;
import com.zone24x7.rac.configservice.recengine.rec.RecEngineRecService;
import com.zone24x7.rac.configservice.recengine.recslot.RecEngineRecSlotService;
import com.zone24x7.rac.configservice.recengine.rule.RecEngineRuleService;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class relating to Rec Engine.
 *
 */
@Service
public class RecEngineService {

    @Autowired
    private RecEngineBundleService recEngineBundleService;

    @Autowired
    private RecEngineRecService recEngineRecService;

    @Autowired
    private RecEngineRuleService recEngineRuleService;

    @Autowired
    private RecEngineRecSlotService recEngineRecSlotService;



    /**
     * Get bundle config json.
     *
     * @return Bundle config json
     */
    public String getBundleConfig() {
        return recEngineBundleService.getBundleConfig();
    }

    /**
     * Get rule config json.
     *
     * @return Rule config json
     */
    public String getRuleConfig() {
        return recEngineRuleService.getRuleConfig();
    }

    /**
     * Get recs config json.
     *
     * @return Recs config json
     */
    public String getRecsConfig() {
        return recEngineRecService.getRecsConfig();
    }

    /**
     * Get rec slots config json.
     *
     * @return Rec slots config json
     */
    public String getRecSlotsConfig() {
        return recEngineRecSlotService.getRecSlotsConfig();
    }







    /**
     * Update bundle config json.
     *
     * @throws ServerException when bundle config update failed.
     */
    public void updateBundleConfig() throws ServerException {
        recEngineBundleService.updateBundleConfig();
    }

    /**
     * Update rule config json.
     *
     * @throws ServerException when rule config update failed.
     */
    public void updateRuleConfig() throws ServerException {
        recEngineRuleService.updateRuleConfig();
    }

    /**
     * Update rec config json.
     *
     * @throws ServerException when rec config update failed.
     */
    public void updateRecConfig() throws ServerException {
        recEngineRecService.updateRecConfig();
    }


    /**
     * Update rec slot config json.
     *
     * @throws ServerException when rec slot config update failed.
     */
    public void updateRecSlotConfig() throws ServerException {
        recEngineRecSlotService.updateRecSlotConfig();
    }













    /**
     * Add bundle config json.
     *
     * @param bundleConfig Bundle Json
     * @return status response
     */
    public CSResponse addBundleConfig(String bundleConfig) {
        return recEngineBundleService.addBundleConfig(bundleConfig);
    }

    /**
     * Add rule config json.
     *
     * @param ruleConfig Rule config json
     * @return  Response
     */
    public CSResponse addRuleConfig(String ruleConfig) {
        return recEngineRuleService.addRuleConfig(ruleConfig);
    }

    /**
     * Add rec Json.
     *
     * @param recConfig Rec config json
     * @return Response
     */
    public CSResponse addRecConfig(String recConfig) {
        return recEngineRecService.addRecConfig(recConfig);
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotConfig Rec slot config json
     * @return  Response
     */
    public CSResponse addRecSlotConfig(String recSlotConfig) {
        return recEngineRecSlotService.addRecSlotConfig(recSlotConfig);
    }


}
