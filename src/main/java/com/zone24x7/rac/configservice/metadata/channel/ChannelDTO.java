package com.zone24x7.rac.configservice.metadata.channel;

public class ChannelDTO {

    private int id;
    private String name;

    public ChannelDTO() {
    }

    public ChannelDTO(int id, String name) {
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
