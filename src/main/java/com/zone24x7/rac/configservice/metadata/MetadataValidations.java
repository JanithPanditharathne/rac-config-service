package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;

import static com.zone24x7.rac.configservice.util.Strings.*;

public final class MetadataValidations {

    private MetadataValidations() {
    }


    /**
     * Validate metadata type.
     *
     * @param type metadata type
     * @throws ValidationException for invalid type.
     */
    public static void validateType(String type) throws ValidationException {
        // Validate for null.
        if (type == null) {
            throw new ValidationException(METADATA_TYPE_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (type.isEmpty()) {
            throw new ValidationException(METADATA_TYPE_CANNOT_BE_EMPTY);
        }
    }



    /**
     * Validate metadata name.
     *
     * @param name metadata name
     * @throws ValidationException for invalid name.
     */
    public static void validateName(String name) throws ValidationException {
        // Validate for null.
        if (name == null) {
            throw new ValidationException(METADATA_NAME_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(METADATA_NAME_CANNOT_BE_EMPTY);
        }
    }
}
