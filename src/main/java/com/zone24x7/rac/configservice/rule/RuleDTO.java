package com.zone24x7.rac.configservice.rule;

import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import java.util.List;

public class RuleDTO {

    private int id;
    private String name;
    private String type;
    private boolean isGlobal;
    private String matchingCondition;
    private List<BaseExpr> matchingConditionJson;
    private String actionCondition;
    private List<BaseExpr> actionConditionJson;

    public RuleDTO() {
    }

    public RuleDTO(int id, String name, String type, boolean isGlobal, String matchingCondition,
                   List<BaseExpr> matchingConditionJson, String actionCondition, List<BaseExpr> actionConditionJson) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isGlobal = isGlobal;
        this.matchingCondition = matchingCondition;
        this.matchingConditionJson = matchingConditionJson;
        this.actionCondition = actionCondition;
        this.actionConditionJson = actionConditionJson;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public String getMatchingCondition() {
        return matchingCondition;
    }

    public void setMatchingCondition(String matchingCondition) {
        this.matchingCondition = matchingCondition;
    }

    public List<BaseExpr> getMatchingConditionJson() {
        return matchingConditionJson;
    }

    public void setMatchingConditionJson(List<BaseExpr> matchingConditionJson) {
        this.matchingConditionJson = matchingConditionJson;
    }

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }

    public List<BaseExpr> getActionConditionJson() {
        return actionConditionJson;
    }

    public void setActionConditionJson(List<BaseExpr> actionConditionJson) {
        this.actionConditionJson = actionConditionJson;
    }
}
