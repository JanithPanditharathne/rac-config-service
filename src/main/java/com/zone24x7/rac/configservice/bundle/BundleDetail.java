package com.zone24x7.rac.configservice.bundle;

import java.util.List;

public class BundleDetail {

    private int id;
    private String name;
    private int defaultLimit;
    private List<BundleAlgorithmDetail> algorithms;
    private boolean combineEnabled;
    private String combineDisplayText;


    public BundleDetail() {
    }

    public BundleDetail(int id, String name, int defaultLimit, boolean combineEnabled, String combineDisplayText,
                        List<BundleAlgorithmDetail> algorithms) {
        this.id = id;
        this.name = name;
        this.defaultLimit = defaultLimit;
        this.combineEnabled = combineEnabled;
        this.combineDisplayText = combineDisplayText;
        this.algorithms = algorithms;
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

    public List<BundleAlgorithmDetail> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<BundleAlgorithmDetail> algorithms) {
        this.algorithms = algorithms;
    }
}
