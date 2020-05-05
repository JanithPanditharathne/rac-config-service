package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * Add bundle json.
     *
     * @param config Bundle config json
     * @return Response
     */
    @RequestMapping(path = "/recEngine/bundles", method = RequestMethod.POST)
    public CSResponse addBundleConfig(@RequestBody String config) {
        return recEngineService.addBundleConfig(config);
    }

    /**
     * Add rule json.
     *
     * @param ruleJson Rule config json
     * @return Response
     */
    @RequestMapping(path = "/recEngine/rules", method = RequestMethod.POST)
    public CSResponse addRuleConfig(@RequestBody String ruleJson) {
        return recEngineService.addRuleConfig(ruleJson);
    }

    /**
     * Add rec config json.
     *
     * @param recJson Rec config json
     * @return Response
     */
    @RequestMapping(path = "/recEngine/recs", method = RequestMethod.POST)
    public CSResponse addRecConfig(@RequestBody String recJson) {
        return recEngineService.addRecConfig(recJson);
    }

    /**
     * Add rec slot config json.
     *
     * @param recSlotJson Rec slot config json
     * @return Response
     */
    @RequestMapping(path = "/recEngine/recSlots", method = RequestMethod.POST)
    public CSResponse addRecSlotConfig(@RequestBody String recSlotJson) {
        return recEngineService.addRecSlotConfig(recSlotJson);
    }

    /**
     * Get bundle config.
     *
     * @return Bundle config
     */
    @RequestMapping(path = "/recEngine/bundles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBundleConfig() {
        return recEngineService.getBundleConfig();
    }

    /**
     * Get rule config.
     *
     * @return Rule config
     */
    @RequestMapping(path = "/recEngine/rules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRuleConfig() {
        return recEngineService.getRuleConfig();
    }

    /**
     * Get recs config.
     *
     * @return Recs config
     */
    @RequestMapping(path = "/recEngine/recs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecConfig() {
        return recEngineService.getRecsConfig();
    }

    /**
     * Get rec slots config.
     *
     * @return Rec slots config
     */
    @RequestMapping(path = "/recEngine/recSlots", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecSlotConfig() {
        return recEngineService.getRecSlotsConfig();
    }
}
