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
}
