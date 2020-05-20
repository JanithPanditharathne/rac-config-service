package com.zone24x7.rac.configservice.algorithm;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

import static com.zone24x7.rac.configservice.util.Strings.ALGORITHM_NAME_CANNOT_BE_EMPTY;
import static com.zone24x7.rac.configservice.util.Strings.ALGORITHM_NAME_CANNOT_BE_NULL;

public final class AlgorithmValidations {


    private AlgorithmValidations() {
    }


    /**
     * Validate algorithm id.
     *
     * @param id algorithm is
     * @throws ValidationException for invalid id.
     */
    public static void validateID(int id) throws ValidationException {
        if(id < 1) {
            throw new ValidationException(Strings.ALGORITHM_ID_INVALID);
        }
    }


    /**
     * Validate algorithm name.
     *
     * @param name algorithm name
     * @throws ValidationException for invalid name.
     */
    public static void validateName(String name) throws ValidationException {
        // Validate for null.
        if (name == null) {
            throw new ValidationException(ALGORITHM_NAME_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(ALGORITHM_NAME_CANNOT_BE_EMPTY);
        }
    }
}
