package com.zone24x7.rac.configservice.rule;

import java.util.List;

public class RuleList {

    private List<Object> rules;

    public RuleList() {
    }

    public RuleList(List<Object> rules) {
        this.rules = rules;
    }

    public List<Object> getRules() {
        return rules;
    }

    public void setRules(List<Object> rules) {
        this.rules = rules;
    }
}
