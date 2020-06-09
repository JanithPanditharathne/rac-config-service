package com.zone24x7.rac.configservice.rule.expression.price;

public class PriceValue {

    private String operator;
    private Double price;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
