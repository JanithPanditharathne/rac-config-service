package com.zone24x7.rac.configservice.bundle;

import java.util.List;

public class BundleDetailDTO {

    private String id;
    private String name;
    private int defaultLimit;
    private boolean combineEnabled;
    private String combineDisplayText;
    private List<BundleAlgorithmDTO> algorithms;

    public BundleDetailDTO() {
    }

    public BundleDetailDTO(String id, String name, int defaultLimit, boolean combineEnabled, String combineDisplayText,
                           List<BundleAlgorithmDTO> algorithms) {
        this.id = id;
        this.name = name;
        this.defaultLimit = defaultLimit;
        this.combineEnabled = combineEnabled;
        this.combineDisplayText = combineDisplayText;
        this.algorithms = algorithms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
