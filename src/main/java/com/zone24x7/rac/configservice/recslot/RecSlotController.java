package com.zone24x7.rac.configservice.recslot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RecSlotController {

    @Autowired
    private RecSlotService recSlotService;

    /**
     * Get all rec slots.
     *
     * @return Rec slots list
     */
    @GetMapping("/recSlots")
    public RecSlotList getAllRecSlots() {
        return recSlotService.getAllRecSlots();
    }
}
