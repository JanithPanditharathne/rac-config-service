package com.zone24x7.rac.configservice.rule.expression.price;

public class PriceValue {

    private String operator;
    private Double price;

    public PriceValue() {
    }

    public PriceValue(String operator, Double price) {
        this.operator = operator;
        this.price = price;
    }

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
