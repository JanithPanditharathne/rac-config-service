package com.zone24x7.rac.configservice.rule;

import java.util.List;

public class RuleDetail {

    private int id;
    private String name;
    private String type;
    private boolean isGlobal;
    private List<BaseRuleExpression> matchingConditionJson;
    private List<BaseRuleExpression> actionConditionJson;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public List<BaseRuleExpression> getMatchingConditionJson() {
        return matchingConditionJson;
    }

    public void setMatchingConditionJson(List<BaseRuleExpression> matchingConditionJson) {
        this.matchingConditionJson = matchingConditionJson;
    }

    public List<BaseRuleExpression> getActionConditionJson() {
        return actionConditionJson;
    }

    public void setActionConditionJson(List<BaseRuleExpression> actionConditionJson) {
        this.actionConditionJson = actionConditionJson;
    }
}
