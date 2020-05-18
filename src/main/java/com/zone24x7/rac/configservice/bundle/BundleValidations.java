package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.algorithm.AlgorithmValidations;
import com.zone24x7.rac.configservice.exception.ValidationException;

import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.*;

/**
 * Class to validate bundle related fields.
 *
 */
public class BundleValidations {

    private BundleValidations() {
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
    public static void validateAlgorithms(List<BundleAlgorithmDTO> algorithms) throws ValidationException {

        // Validate for null.
        if (algorithms == null) {
            throw new ValidationException(ALGORITHMS_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (algorithms.isEmpty()) {
            throw new ValidationException(ALGORITHMS_CANNOT_BE_EMPTY);
        }

        // Iterate algorithms list.
        for (BundleAlgorithmDTO bundleAlgorithmDTO : algorithms) {

            // Validate ID.
            AlgorithmValidations.validateID(bundleAlgorithmDTO.getId());

            // Validate custom display text.
            validateCustomDisplayText(bundleAlgorithmDTO.getCustomDisplayText());
        }
    }

    /**
     * Validate custom display text.
     *
     * @param customDisplayText Custom display text
     * @throws ValidationException Exception to throw
     */
    private static void validateCustomDisplayText(String customDisplayText) throws ValidationException {

        // Validate for null.
        if (customDisplayText == null) {
            throw new ValidationException(BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (customDisplayText.isEmpty()) {
            throw new ValidationException(BUNDLE_CUSTOM_DISPLAY_TEXT_CANNOT_BE_EMPTY);
        }
    }
}
