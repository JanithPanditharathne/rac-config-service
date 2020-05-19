package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for rec engine.
 *
 */
@RestController
@RequestMapping("/v1")
public class RecEngineController {

    @Autowired
    private RecEngineService recEngineService;


    /**
     * Get bundle config.
     *
     * @return Bundle config
     */
    @GetMapping(path = "/recEngine/bundles", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBundleConfig() {
        return recEngineService.getBundleConfig();
    }

    /**
     * Get rule config.
     *
     * @return Rule config
     */
    @GetMapping(path = "/recEngine/rules", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRuleConfig() {
        return recEngineService.getRuleConfig();
    }

    /**
     * Get recs config.
     *
     * @return Recs config
     */
    @GetMapping(path = "/recEngine/recs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecConfig() {
        return recEngineService.getRecsConfig();
    }

    /**
     * Get rec slots config.
     *
     * @return Rec slots config
     */
    @GetMapping(path = "/recEngine/recSlots", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecSlotConfig() {
        return recEngineService.getRecSlotsConfig();
    }









    /**
     * Update all configs.
     *
     * @return update process status.
     * @throws ServerException if any server errors occurs.
     */
    @PutMapping("/recEngine/update/all")
    public CSResponse updateAllConfigs() throws ServerException {

        // Update bundle configs.
        recEngineService.updateBundleConfig();

        // Update rule configs.
        recEngineService.updateRuleConfig();

        // Update rec configs.
        recEngineService.updateRecConfig();

        // Update rec slot configs.
        recEngineService.updateRecSlotConfig();

        // Return status.
        return new CSResponse(Strings.SUCCESS, Strings.REC_ENGINE_CONFIG_UPDATE_PROCESS_STARTED);
    }









    /**
     * Add bundle json.
     *
     * @param config Bundle config json
     * @return Response
     */
    @PostMapping("/recEngine/bundles")
    public CSResponse addBundleConfig(@RequestBody String config) {
        return recEngineService.addBundleConfig(config);
    }

    /**
     * Add rule json.
     *
     * @param ruleJson Rule config json
     * @return Response
     */
    @PostMapping("/recEngine/rules")
    public CSResponse addRuleConfig(@RequestBody String ruleJson) {
        return recEngineService.addRuleConfig(ruleJson);
    }

    /**
     * Add rec config json.
     *
     * @param recJson Rec config json
     * @return Response
     */
    @PostMapping("/recEngine/recs")
    public CSResponse addRecConfig(@RequestBody String recJson) {
        return recEngineService.addRecConfig(recJson);
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotJson Rec slot config json
     * @return Response
     */
    @PostMapping("/recEngine/recSlots")
    public CSResponse addRecSlotConfig(@RequestBody String recSlotJson) {
        return recEngineService.addRecSlotConfig(recSlotJson);
    }


}
