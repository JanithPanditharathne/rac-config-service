package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.zone24x7.rac.configservice.util.Strings.BRAND;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = BrandRuleExpression.class, name = BRAND)})
public class BaseRuleExpression {

    private String type;
    private String condition;

    public BaseRuleExpression() {
    }

    public BaseRuleExpression(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
