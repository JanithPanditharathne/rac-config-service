package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

import static com.zone24x7.rac.configservice.util.Strings.REC_NAME_CANNOT_BE_EMPTY;
import static com.zone24x7.rac.configservice.util.Strings.REC_NAME_CANNOT_BE_NULL;

public final class RecValidations {

    private RecValidations() {
    }


    /**
     * Validate rec id.
     *
     * @param id rec id
     * @throws ValidationException for invalid id.
     */
    public static void validateID(int id) throws ValidationException {
        if(id < 1) {
            throw new ValidationException(Strings.REC_ID_INVALID);
        }
    }

    /**
     * Validate rec name.
     *
     * @param name Name
     * @throws ValidationException Exception to throw
     */
    public static void validateRecName(String name) throws ValidationException {

        // Validate for null.
        if (name == null) {
            throw new ValidationException(REC_NAME_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(REC_NAME_CANNOT_BE_EMPTY);
        }
    }
}
