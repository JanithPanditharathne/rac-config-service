package com.zone24x7.rac.configservice.algorithm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Algorithm {

    @Id
    private int id;
    private String name;
    private String description;

    @Column(name = "default_display_text")
    private String defaultDisplayText;

    public Algorithm() {
    }

    public Algorithm(int id, String name, String description, String defaultDisplayText) {
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
