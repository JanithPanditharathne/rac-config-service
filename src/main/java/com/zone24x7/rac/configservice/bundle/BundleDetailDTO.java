package com.zone24x7.rac.configservice.bundle;

import java.util.List;

public class BundleDetailDTO {

    private int id;
    private String name;
    private int defaultLimit;
    private boolean combineEnabled;
    private String combineDisplayText;
    private List<BundleAlgorithmDTO> algorithms;

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

    public int getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public boolean isCombineEnabled() {
        return combineEnabled;
    }

    public void setCombineEnabled(boolean combineEnabled) {
        this.combineEnabled = combineEnabled;
    }

    public String getCombineDisplayText() {
        return combineDisplayText;
    }

    public void setCombineDisplayText(String combineDisplayText) {
        this.combineDisplayText = combineDisplayText;
    }

    public List<BundleAlgorithmDTO> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<BundleAlgorithmDTO> algorithms) {
        this.algorithms = algorithms;
    }
}
