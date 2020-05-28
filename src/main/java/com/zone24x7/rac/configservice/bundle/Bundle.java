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

    public int getId() {    // NOSONAR
        return id;
    }

    public void setId(int id) {     // NOSONAR
        this.id = id;
    }

    public String getName() {   // NOSONAR
        return name;
    }

    public void setName(String name) {  // NOSONAR
        this.name = name;
    }

    public int getDefaultLimit() {      // NOSONAR
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {     // NOSONAR
        this.defaultLimit = defaultLimit;
    }

    public boolean isCombineEnabled() {     // NOSONAR
        return combineEnabled;
    }

    public void setCombineEnabled(boolean combineEnabled) {     // NOSONAR
        this.combineEnabled = combineEnabled;
    }

    public String getCombineDisplayText() {     // NOSONAR
        return combineDisplayText;
    }

    public void setCombineDisplayText(String combineDisplayText) {      // NOSONAR
        this.combineDisplayText = combineDisplayText;
    }
}
