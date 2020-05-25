package com.zone24x7.rac.configservice.rec;

public class RecDetail {

    private int id;
    private String name;
    private RecBundle bundle;

    public RecDetail() {
    }

    public RecDetail(int id, String name, RecBundle bundle) {
        this.id = id;
        this.name = name;
        this.bundle = bundle;
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

    public RecBundle getBundle() {
        return bundle;
    }

    public void setBundle(RecBundle bundle) {
        this.bundle = bundle;
    }
}
