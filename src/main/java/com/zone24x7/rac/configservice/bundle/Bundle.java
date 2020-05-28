package com.zone24x7.rac.configservice.bundle;

import javax.persistence.*;

@Entity
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(name = "default_limit")
    private int defaultLimit;

    @Column(name = "combine_enabled")
    private boolean combineEnabled;

    @Column(name = "combine_display_text")
    private String combineDisplayText;


    public Bundle() {
    }

    public Bundle(String name, int defaultLimit, boolean combineEnabled, String combineDisplayText) {
        this.name = name;
        this.defaultLimit = defaultLimit;
        this.combineEnabled = combineEnabled;
        this.combineDisplayText = combineDisplayText;
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
}
