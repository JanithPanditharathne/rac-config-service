package com.zone24x7.rac.configservice.recengine;

import javax.persistence.*;

/**
 * Class representing the Rec Engine model.
 *
 */
@Entity
public class RecEngine {

    @Id
    private int id;

    @Column(name = "config_type", nullable = false)
    private String configType;

    @Column(name = "config_json")
    private String configJson;

    public RecEngine() {
    }

    public RecEngine(int id, String configType, String configJson) {
        this.id = id;
        this.configType = configType;
        this.configJson = configJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }
}
