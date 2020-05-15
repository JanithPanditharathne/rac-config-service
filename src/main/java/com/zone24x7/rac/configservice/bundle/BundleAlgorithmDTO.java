package com.zone24x7.rac.configservice.bundle;

public class BundleAlgorithmDTO {

    private int id;
    private String name;
    private int rank;
    private String defaultDisplayText;
    private String customDisplayText;

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
