package com.zone24x7.rac.configservice.bundle;

public class BundleSummaryDTO {

    private int id;
    private String name;

    public BundleSummaryDTO() {
    }

    public BundleSummaryDTO(int id, String name) {
        this.id = id;
        this.name = name;
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
}
