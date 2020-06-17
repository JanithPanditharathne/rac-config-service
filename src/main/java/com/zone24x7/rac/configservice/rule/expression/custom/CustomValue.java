package com.zone24x7.rac.configservice.rule.expression.custom;

import java.util.List;

public class CustomValue {

    private String key;
    private List<String> values;

    public CustomValue() {
        // Constructor
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
