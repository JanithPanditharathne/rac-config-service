package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Rec Engine.
 *
 */
@RestController
@RequestMapping("/v1")
public class RecEngineController {

    private RecEngineService recEngineService;

    @Autowired
    public RecEngineController(RecEngineService recEngineService) {
        this.recEngineService = recEngineService;
    }

    /**
     * Add bundle JSON.
     *
     * @param bundleJson Bundle JSON
     * @return           Response entity
     */
    @RequestMapping(path = "/recEngine/bundles", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> addBundleJsonForRecEngine(@RequestBody String bundleJson) {

        Response response = recEngineService.addBundleJson(bundleJson);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add rule JSON.
     *
     * @param ruleJson Rule JSON
     * @return         Response entity
     */
    @RequestMapping(path = "/recEngine/rules", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> addRuleJsonForRecEngine(@RequestBody String ruleJson) {

        Response response = recEngineService.addRuleJson(ruleJson);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add rec JSON.
     *
     * @param recJson Rec JSON
     * @return        Response entity
     */
    @RequestMapping(path = "/recEngine/recs", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> addRecJsonForRecEngine(@RequestBody String recJson) {

        Response response = recEngineService.addRecJson(recJson);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Add rec slot JSON.
     *
     * @param recSlotJson Rec slot JSON
     * @return            Response entity
     */
    @RequestMapping(path = "/recEngine/recSlots", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> addRecSlotsJsonForRecEngine(@RequestBody String recSlotJson) {

        Response response = recEngineService.addRecSlotJson(recSlotJson);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
