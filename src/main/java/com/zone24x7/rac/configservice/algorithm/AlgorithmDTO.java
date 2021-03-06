package com.zone24x7.rac.configservice.algorithm;

public class AlgorithmDTO {

    private int id;
    private String name;
    private String description;
    private String defaultDisplayText;

    public AlgorithmDTO() {
    }

    public AlgorithmDTO(int id, String name, String description, String defaultDisplayText) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultDisplayText = defaultDisplayText;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultDisplayText() {
        return defaultDisplayText;
    }

    public void setDefaultDisplayText(String defaultDisplayText) {
        this.defaultDisplayText = defaultDisplayText;
    }
}
