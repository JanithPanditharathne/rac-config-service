package com.zone24x7.rac.configservice.rule.expression.brand;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BRAND;

public class BrandExpr extends BaseExpr {

    private List<String> value;

    public BrandExpr() {
        super(BRAND);
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
