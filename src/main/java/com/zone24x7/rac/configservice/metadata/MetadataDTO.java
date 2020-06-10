package com.zone24x7.rac.configservice.metadata;

public class MetadataDTO {

    private int id;
    private String name;
    private String type;

    public MetadataDTO() {
    }

    public MetadataDTO(String name, String type) {
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
