package com.zone24x7.rac.configservice.bundle;

public class BundleAlgorithmDetail {

    private int rank;
    private int id;
    private String name;
    private String defaultDisplayText;
    private String customDisplayText;

    public BundleAlgorithmDetail() {
    }

    public BundleAlgorithmDetail(int id, String name, int rank, String defaultDisplayText, String customDisplayText) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.defaultDisplayText = defaultDisplayText;
        this.customDisplayText = customDisplayText;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDefaultDisplayText() {
        return defaultDisplayText;
    }

    public void setDefaultDisplayText(String defaultDisplayText) {
        this.defaultDisplayText = defaultDisplayText;
    }

    public String getCustomDisplayText() {
        return customDisplayText;
    }

    public void setCustomDisplayText(String customDisplayText) {
        this.customDisplayText = customDisplayText;
    }
}
