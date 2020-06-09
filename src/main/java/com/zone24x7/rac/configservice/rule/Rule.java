package com.zone24x7.rac.configservice.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.rule.expression.BaseExpr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "matching_condition")
    private String matchingCondition;

    @Column(name = "matching_condition_json")
    private String matchingConditionJson;

    @Column(name = "action_condition")
    private String actionCondition;

    @Column(name = "action_condition_json")
    private String actionConditionJson;

    public Rule() {
    }

    public Rule(String name, String type, boolean isGlobal, String matchingCondition, String matchingConditionJson,
                String actionCondition, String actionConditionJson) {
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

    public boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public String getMatchingConditionJson() {
        return matchingConditionJson;
    }

    public void setMatchingConditionJson(String matchingConditionJson) {
        this.matchingConditionJson = matchingConditionJson;
    }

    public String getActionConditionJson() {
        return actionConditionJson;
    }

    public void setActionConditionJson(String actionConditionJson) {
        this.actionConditionJson = actionConditionJson;
    }

    public String getMatchingCondition() {
        return matchingCondition;
    }

    public void setMatchingCondition(String matchingCondition) {
        this.matchingCondition = matchingCondition;
    }

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }

    public List<BaseExpr> getMatchingConditionJsonAsList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return Collections.singletonList(objectMapper.readValue(this.matchingConditionJson, BaseExpr.class));
    }

    public List<BaseExpr> getActionConditionJsonAsList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return Collections.singletonList(objectMapper.readValue(this.actionConditionJson, BaseExpr.class));
    }
}
