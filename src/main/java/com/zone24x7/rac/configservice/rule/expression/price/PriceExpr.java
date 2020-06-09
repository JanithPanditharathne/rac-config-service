package com.zone24x7.rac.configservice.rule.expression.price;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import static com.zone24x7.rac.configservice.util.Strings.PRICE;

public class PriceExpr extends BaseExpr {

    private PriceValue value;

    public PriceExpr() {
        super(PRICE);
    }

    public PriceValue getValue() {
        return value;
    }

    public void setValue(PriceValue value) {
        this.value = value;
    }
}
