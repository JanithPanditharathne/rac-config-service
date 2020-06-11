package com.zone24x7.rac.configservice.rule.expression.brand;

public class BrandValue {

    private int id;
    private String name;

    public BrandValue() {
    }

    public BrandValue(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
