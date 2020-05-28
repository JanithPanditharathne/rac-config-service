package com.zone24x7.rac.configservice.rec;

import javax.persistence.*;

@Entity
public class Rec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(name = "bundle_id")
    private int bundleID;

    public Rec() {
    }

    public Rec(int id, String name, int bundleID) {
        this.id = id;
        this.name = name;
        this.bundleID = bundleID;
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

    public int getBundleID() {
        return bundleID;
    }

    public void setBundleID(int bundleID) {
        this.bundleID = bundleID;
    }
}
