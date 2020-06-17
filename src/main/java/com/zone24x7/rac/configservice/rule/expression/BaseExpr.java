package com.zone24x7.rac.configservice.rule.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zone24x7.rac.configservice.rule.expression.brand.BrandExpr;
import com.zone24x7.rac.configservice.rule.expression.custom.CustomExpr;
import com.zone24x7.rac.configservice.rule.expression.price.PriceExpr;
import com.zone24x7.rac.configservice.rule.expression.productnumber.ProductNumberExpr;

import static com.zone24x7.rac.configservice.util.Strings.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BrandExpr.class, name = BRAND),
        @JsonSubTypes.Type(value = ProductNumberExpr.class, name = PRODUCT_NUMBER),
        @JsonSubTypes.Type(value = PriceExpr.class, name = PRICE),
        @JsonSubTypes.Type(value = CustomExpr.class, name = CUSTOM)
})
public class BaseExpr {

    private String type;
    private String condition;
    private String operator;

    public BaseExpr() {
    }

    public BaseExpr(String ruleType) {
        this.type = ruleType;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
