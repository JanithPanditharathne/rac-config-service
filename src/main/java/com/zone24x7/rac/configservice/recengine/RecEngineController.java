package com.zone24x7.rac.configservice.recengine;

import com.zone24x7.rac.configservice.utils.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Rec Engine.
 *
 */
@RestController
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
    @PostMapping(path = "/v1/recEngine/bundles", consumes = "application/json")
    public ResponseEntity<Object> addBundleJsonForRecEngine(@RequestBody String bundleJson) {

        MessageDTO messageDTO = recEngineService.addBundleJson(bundleJson);

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    /**
     * Add rule JSON.
     *
     * @param ruleJson Rule JSON
     * @return         Response entity
     */
    @PostMapping(path = "/v1/recEngine/rules", consumes = "application/json")
    public ResponseEntity<Object> addRuleJsonForRecEngine(@RequestBody String ruleJson) {

        MessageDTO messageDTO = recEngineService.addRuleJson(ruleJson);

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    /**
     * Add rec JSON.
     *
     * @param recJson Rec JSON
     * @return        Response entity
     */
    @PostMapping(path = "/v1/recEngine/recs", consumes = "application/json")
    public ResponseEntity<Object> addRecJsonForRecEngine(@RequestBody String recJson) {

        MessageDTO messageDTO = recEngineService.addRecJson(recJson);

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    /**
     * Add rec slot JSON.
     *
     * @param recSlotJson Rec slot JSON
     * @return            Response entity
     */
    @PostMapping(path = "/v1/recEngine/recSlots", consumes = "application/json")
    public ResponseEntity<Object> addRecSlotsJsonForRecEngine(@RequestBody String recSlotJson) {

        MessageDTO messageDTO = recEngineService.addRecSlotJson(recSlotJson);

        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
