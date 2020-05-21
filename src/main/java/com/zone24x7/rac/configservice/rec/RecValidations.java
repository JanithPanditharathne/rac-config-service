package com.zone24x7.rac.configservice.rec;

import com.zone24x7.rac.configservice.exception.ValidationException;

import static com.zone24x7.rac.configservice.util.Strings.*;

public class RecValidations {

    private RecValidations() {
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
            throw new ValidationException(REC_NAME_CANN0T_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(REC_NAME_CANN0T_BE_EMPTY);
        }
    }
}
