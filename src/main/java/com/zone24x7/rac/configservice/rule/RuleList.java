package com.zone24x7.rac.configservice.rule;

import java.util.List;

public class RuleList {

    private List<Rule> rules;

    public RuleList() {
    }

    public RuleList(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
