package com.zone24x7.rac.configservice.recengine.bundle;

public class RecEngineBundleAlgorithm {

    private int id;
    private String name;
    private String type;
    private String defaultDisplayText;
    private String customDisplayText;

    public RecEngineBundleAlgorithm() {
    }

    public RecEngineBundleAlgorithm(int id, String name, String type, String defaultDisplayText,
                                    String customDisplayText) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
