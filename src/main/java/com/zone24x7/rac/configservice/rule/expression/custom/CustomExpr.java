package com.zone24x7.rac.configservice.rule.expression.custom;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import static com.zone24x7.rac.configservice.util.Strings.CUSTOM;

public class CustomExpr extends BaseExpr {

    private CustomValue value;

    public CustomExpr() {
        super(CUSTOM);
    }

    public CustomValue getValue() {
        return value;
    }

    public void setValue(CustomValue value) {
        this.value = value;
    }
}
