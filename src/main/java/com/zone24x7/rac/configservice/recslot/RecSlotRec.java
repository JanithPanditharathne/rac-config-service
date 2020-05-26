package com.zone24x7.rac.configservice.recslot;

public class RecSlotRec {

    private int id;
    private String name;

    public RecSlotRec() {
    }

    public RecSlotRec(int id, String name) {
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
