package com.zone24x7.rac.configservice.rule;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.zone24x7.rac.configservice.util.Strings.*;

public final class RuleValidations {

    private static final Set<String> RULE_TYPES = new HashSet<>(Arrays.asList(BOOST, BURY, ONLY_RECOMMEND, DO_NOT_RECOMMEND));

    private RuleValidations() {
    }

    /**
     * Validate rule id.
     *
     * @param id rule id
     * @throws ValidationException for invalid id
     */
    public static void validateID(int id) throws ValidationException {
        if (id < 1) {
            throw new ValidationException(Strings.RULE_ID_INVALID);
        }
    }

    /**
     * Validate rule name.
     *
     * @param name Rule name
     * @throws ValidationException Validation exception to throw
     */
    public static void validateName(String name) throws ValidationException {

        // Validate for null.
        if (name == null) {
            throw new ValidationException(RULE_NAME_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (name.isEmpty()) {
            throw new ValidationException(RULE_NAME_CANNOT_BE_EMPTY);
        }
    }

    /**
     * Validate rule type.
     *
     * @param type Rule type
     * @throws ValidationException Validation exception to throw
     */
    public static void validateType(String type) throws ValidationException {

        // Validate for null.
        if (type == null) {
            throw new ValidationException(RULE_TYPE_CANNOT_BE_NULL);
        }

        // Validate for empty.
        if (type.isEmpty()) {
            throw new ValidationException(RULE_TYPE_CANNOT_BE_EMPTY);
        }

        // Validate for correct value.
        if (!RULE_TYPES.contains(type)) {
            throw new ValidationException(RULE_TYPE_INVALID);
        }
    }
}
