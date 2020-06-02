package com.zone24x7.rac.configservice.rule;

import java.util.List;

public class RuleList {

    private List<RuleDetail> rules;

    public RuleList() {
    }

    public RuleList(List<RuleDetail> rules) {
        this.rules = rules;
    }

    public List<RuleDetail> getRules() {
        return rules;
    }

    public void setRules(List<RuleDetail> rules) {
        this.rules = rules;
    }
}
