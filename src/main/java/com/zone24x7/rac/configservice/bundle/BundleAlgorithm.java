package com.zone24x7.rac.configservice.bundle;

import javax.persistence.*;

@Entity
public class BundleAlgorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bundle_id")
    private int bundleID;

    @Column(name = "algorithm_id")
    private int algorithmID;

    @Column(name = "custom_display_text")
    private String customDisplayText;

    private int rank;


    public BundleAlgorithm(int bundleID, int algorithmID, String customDisplayText, int rank) {
        this.bundleID = bundleID;
        this.algorithmID = algorithmID;
        this.customDisplayText = customDisplayText;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBundleID() {
        return bundleID;
    }

    public void setBundleID(int bundleID) {
        this.bundleID = bundleID;
    }

    public int getAlgorithmID() {
        return algorithmID;
    }

    public void setAlgorithmID(int algorithmID) {
        this.algorithmID = algorithmID;
    }

    public String getCustomDisplayText() {
        return customDisplayText;
    }

    public void setCustomDisplayText(String customDisplayText) {
        this.customDisplayText = customDisplayText;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
