package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
}
