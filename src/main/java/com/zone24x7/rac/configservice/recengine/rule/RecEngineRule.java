package com.zone24x7.rac.configservice.recengine.rule;

public class RecEngineRule {

    private int id;
    private String name;
    private String type;
    private boolean isGlobal;
    private String matchingCondition;
    private String actionCondition;


    public RecEngineRule() {
    }

    public RecEngineRule(int id, String name, String type, boolean isGlobal, String matchingCondition,
                         String actionCondition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isGlobal = isGlobal;
        this.matchingCondition = matchingCondition;
        this.actionCondition = actionCondition;
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

    public String getActionCondition() {
        return actionCondition;
    }

    public void setActionCondition(String actionCondition) {
        this.actionCondition = actionCondition;
    }
}
