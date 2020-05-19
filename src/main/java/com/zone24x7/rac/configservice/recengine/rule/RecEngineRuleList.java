package com.zone24x7.rac.configservice.recengine.rule;

import java.util.List;

public class RecEngineRuleList {

    private List<RecEngineRule> rules;

    public RecEngineRuleList() {
    }

    public RecEngineRuleList(List<RecEngineRule> rules) {
        this.rules = rules;
    }

    public List<RecEngineRule> getRules() {
        return rules;
    }

    public void setRules(List<RecEngineRule> rules) {
        this.rules = rules;
    }
}
