package com.zone24x7.rac.configservice.rule.expression.brand;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BRAND;

public class BrandExpr extends BaseExpr {

    private List<BrandValue> value;

    public BrandExpr() {
        super(BRAND);
    }

    public List<BrandValue> getValue() {
        return value;
    }

    public void setValue(List<BrandValue> value) {
        this.value = value;
    }
}
