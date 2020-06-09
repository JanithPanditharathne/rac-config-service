package com.zone24x7.rac.configservice.rule;

import java.util.List;

public class RuleList {

    private List<RuleDTO> rules;

    public RuleList() {
    }

    public RuleList(List<RuleDTO> rules) {
        this.rules = rules;
    }

    public List<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<RuleDTO> rules) {
        this.rules = rules;
    }
}
