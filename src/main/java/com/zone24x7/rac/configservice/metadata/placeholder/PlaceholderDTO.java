package com.zone24x7.rac.configservice.metadata.placeholder;

public class PlaceholderDTO {

    private int id;
    private String name;

    public PlaceholderDTO() {
    }

    public PlaceholderDTO(int id, String name) {
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
