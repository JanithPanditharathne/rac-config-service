package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

public final class RecSlotValidations {

    private RecSlotValidations() {
    }

    /**
     * Validate rec slot id.
     *
     * @param id rec slot id
     * @throws ValidationException for invalid id.
     */
    public static void validateID(int id) throws ValidationException {
        if(id < 1) {
            throw new ValidationException(Strings.REC_SLOT_ID_INVALID);
        }
    }
}
