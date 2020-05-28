package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ServerException;
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
     * Get rec slot by id.
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
     * @param recSlotDetail Rec slot detail
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    @PostMapping("/recSlots")
    public CSResponse addRecSlot(@RequestBody RecSlotDetail recSlotDetail) throws ValidationException, ServerException {
        return recSlotService.addRecSlot(recSlotDetail);
    }

    /**
     * Edit rec slot.
     *
     * @param recSlotDetail Rec slot detail
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    @PutMapping("/recSlots/{id}")
    public CSResponse editRecSlot(@PathVariable int id, @RequestBody RecSlotDetail recSlotDetail) throws ValidationException, ServerException {
        return recSlotService.editRecSlot(id, recSlotDetail);
    }
}
