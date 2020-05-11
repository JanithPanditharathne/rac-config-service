package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
     * Add bundle json.
     *
     * @param config Bundle config json
     * @return Response
     */
    @PostMapping(path = "/recEngine/bundles")
    public CSResponse addBundleConfig(@RequestBody String config) {
        return recEngineService.addBundleConfig(config);
    }

    /**
     * Add rule json.
     *
     * @param ruleJson Rule config json
     * @return Response
     */
    @PostMapping(path = "/recEngine/rules")
    public CSResponse addRuleConfig(@RequestBody String ruleJson) {
        return recEngineService.addRuleConfig(ruleJson);
    }

    /**
     * Add rec config json.
     *
     * @param recJson Rec config json
     * @return Response
     */
    @PostMapping(path = "/recEngine/recs")
    public CSResponse addRecConfig(@RequestBody String recJson) {
        return recEngineService.addRecConfig(recJson);
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotJson Rec slot config json
     * @return Response
     */
    @PostMapping(path = "/recEngine/recSlots")
    public CSResponse addRecSlotConfig(@RequestBody String recSlotJson) {
        return recEngineService.addRecSlotConfig(recSlotJson);
    }


}
