package com.zone24x7.rac.configservice.rule.expression.productnumber;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import static com.zone24x7.rac.configservice.util.Strings.PRODUCT_NUMBER;

public class ProductNumberExpr extends BaseExpr {

    private String[] value;

    public ProductNumberExpr() {
        super(PRODUCT_NUMBER);
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }
}
