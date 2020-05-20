package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.ALGORITHMS_CANNOT_BE_EMPTY;
import static com.zone24x7.rac.configservice.util.Strings.ALGORITHMS_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_NAME_CANNOT_BE_EMPTY;
import static com.zone24x7.rac.configservice.util.Strings.BUNDLE_NAME_CANNOT_BE_NULL;

/**
 * Class to validate bundle related fields.
 *
 */
public final class BundleValidations {

    private BundleValidations() {
    }


    /**
     * Validate bundle id.
     *
     * @param id bundle id
     * @throws ValidationException for invalid id.
     */
    public static void validateID(int id) throws ValidationException {
        if(id < 1) {
            throw new ValidationException(Strings.BUNDLE_ID_INVALID);
        }
    }


    /**
     * Validate bundle name.
     *
     * @param name Bundle name
     * @throws ValidationException Exception to throw
     */
    public static void validateName(String name) throws ValidationException {

        // Validate for null.
        if (name == null) {
            throw new ValidationException(BUNDLE_NAME_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(BUNDLE_NAME_CANNOT_BE_EMPTY);
        }
    }

    /**
     * Validate combine display text.
     *
     * @param combineDisplayText Combine display text
     * @throws ValidationException Exception to throw
     */
    public static void validateCombinedDisplayText(String combineDisplayText) throws ValidationException {

        // Validate for null.
        if (combineDisplayText == null) {
            throw new ValidationException(BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (combineDisplayText.isEmpty()) {
            throw new ValidationException(BUNDLE_COMBINE_DISPLAY_TEXT_CANNOT_BE_EMPTY);
        }
    }

    /**
     * Validate algorithms.
     *
     * @param algorithms Algorithms
     * @throws ValidationException Exception to throw
     */
    public static void validateAlgorithms(List<BundleAlgorithmDetail> algorithms) throws ValidationException {

        // Validate for null.
        if (algorithms == null) {
            throw new ValidationException(ALGORITHMS_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (algorithms.isEmpty()) {
            throw new ValidationException(ALGORITHMS_CANNOT_BE_EMPTY);
        }
    }


}
