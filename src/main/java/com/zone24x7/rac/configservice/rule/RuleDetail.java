package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import java.util.List;

public class RuleDetail {

    private int id;
    private String name;
    private String type;
    private boolean isGlobal;
    private String matchingCondition;
    private List<BaseExpr> matchingConditionJson;
    private String actionCondition;
    private List<BaseExpr> actionConditionJson;

    public RuleDetail() {
        // Constructor
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

    public boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
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

    @JsonIgnore
    public String getMatchingConditionJsonAsString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this.matchingConditionJson);
    }

    @JsonIgnore
    public String getActionConditionJsonAsString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this.actionConditionJson);
    }
}
