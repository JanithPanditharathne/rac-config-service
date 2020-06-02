package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.BRAND;

@JsonTypeName(value = BRAND)
public class BrandRuleExpression extends BaseRuleExpression {

    private List<RuleExpressionValue> value;

    public BrandRuleExpression() {
        super(BRAND);
    }

    public List<RuleExpressionValue> getValue() {
        return value;
    }

    public void setValue(List<RuleExpressionValue> value) {
        this.value = value;
    }
}
