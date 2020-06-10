package com.zone24x7.rac.configservice.rule;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.Strings;

public final class RuleValidations {

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
}
