package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
    @GetMapping("/rec-slots")
    public RecSlotList getAllRecSlots() {
        return recSlotService.getAllRecSlots(true);
    }

    /**
     * Get rec slot by id.
     *
     * @return Rec slot
     * @throws ValidationException Exception to throw
     */
    @GetMapping("/rec-slots/{id}")
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
    @PostMapping("/rec-slots")
    public CSResponse addRecSlot(@RequestBody RecSlotDetail recSlotDetail, HttpServletResponse response) throws ValidationException, ServerException {
        return recSlotService.addRecSlot(recSlotDetail);
    }

    /**
     * Edit rec slot.
     *
     * @param recSlotDetail Rec slot detail
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    @PutMapping("/rec-slots/{id}")
    public CSResponse editRecSlot(@PathVariable int id, @RequestBody RecSlotDetail recSlotDetail) throws ValidationException, ServerException {
        return recSlotService.editRecSlot(id, recSlotDetail);
    }

    /**
     * Delete rec slot.
     *
     * @param id Rec slot id.
     * @return   CS response.
     * @throws ValidationException Validation exception to throw
     * @throws ServerException     Server exception to throw
     */
    @DeleteMapping("/rec-slots/{id}")
    public CSResponse deleteRecSlot(@PathVariable int id) throws ServerException, ValidationException {
        return recSlotService.deleteRecSlot(id);
    }
}
