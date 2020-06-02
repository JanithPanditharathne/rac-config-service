package com.zone24x7.rac.configservice.rule;

import javax.persistence.*;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;

    @Column(name = "is_global")
    private boolean isGlobal;

    @Column(name = "matching_condition_json")
    private String matchingConditionJson;

    @Column(name = "action_condition_json")
    private String actionConditionJson;

    @Column(name = "matching_condition")
    private String matchingCondition;

    @Column(name = "action_condition")
    private String actionCondition;

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
}
