package com.zone24x7.rac.configservice.recslot;

import javax.persistence.*;

@Entity
public class RecSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "channel_id")
    private int channelID;

    @Column(name = "page_id")
    private int pageID;

    @Column(name = "placeholder_id")
    private int placeholderID;

    @Column(name = "rec_id")
    private int recID;

    public RecSlot(int id, int channelID, int pageID, int placeholderID, int recID) {
        this.id = id;
        this.channelID = channelID;
        this.pageID = pageID;
        this.placeholderID = placeholderID;
        this.recID = recID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public int getPlaceholderID() {
        return placeholderID;
    }

    public void setPlaceholderID(int placeholderID) {
        this.placeholderID = placeholderID;
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }
}
