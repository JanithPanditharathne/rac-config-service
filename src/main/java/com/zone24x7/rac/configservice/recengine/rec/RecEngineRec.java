package com.zone24x7.rac.configservice.recengine.rec;

public class RecEngineRec {

    private int id;
    private String name;
    private String type;
    private String matchingCondition;
    private RecEngineRecRegularConfig regularConfig;
    private RecEngineRecTestConfig testConfig;


    public RecEngineRec() {
    }

    public RecEngineRec(int id, String name, String type, String matchingCondition,
                        RecEngineRecRegularConfig regularConfig, RecEngineRecTestConfig testConfig) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.matchingCondition = matchingCondition;
        this.regularConfig = regularConfig;
        this.testConfig = testConfig;
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

    public String getMatchingCondition() {
        return matchingCondition;
    }

    public void setMatchingCondition(String matchingCondition) {
        this.matchingCondition = matchingCondition;
    }

    public RecEngineRecRegularConfig getRegularConfig() {
        return regularConfig;
    }

    public void setRegularConfig(RecEngineRecRegularConfig regularConfig) {
        this.regularConfig = regularConfig;
    }

    public RecEngineRecTestConfig getTestConfig() {
        return testConfig;
    }

    public void setTestConfig(RecEngineRecTestConfig testConfig) {
        this.testConfig = testConfig;
    }
}
