package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get rec slot by ID.
     *
     * @return Rec slot
     * @throws ValidationException Exception to throw
     */
    @GetMapping("/recSlots/{id}")
    public RecSlotDetail getRecSlot(@PathVariable int id) throws ValidationException {
        return recSlotService.getRecSlot(id);
    }

    /**
     * Add new rec slot.
     *
     * @param recSlotSummary Rec slot summary
     * @return               CS Response
     * @throws ValidationException Exception to throw
     */
    @PostMapping("/recSlots")
    public CSResponse addNewRecSlot(@RequestBody RecSlotSummary recSlotSummary) throws ValidationException {
        return recSlotService.addNewRecSlot(recSlotSummary);
    }
}
