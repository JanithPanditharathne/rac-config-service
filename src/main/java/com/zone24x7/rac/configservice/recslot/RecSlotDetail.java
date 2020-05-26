package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;

public class RecSlotDetail {

    private int id;
    private Channel channel;
    private Page page;
    private Placeholder placeholder;
    private RecSlotRec rec;
    private String rules; // TODO: List of rules.

    public RecSlotDetail() {
    }

    public RecSlotDetail(int id, Channel channel, Page page, Placeholder placeholder, RecSlotRec rec, String rules) {
        this.id = id;
        this.channel = channel;
        this.page = page;
        this.placeholder = placeholder;
        this.rec = rec;
        this.rules = rules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Placeholder getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Placeholder placeholder) {
        this.placeholder = placeholder;
    }

    public RecSlotRec getRec() {
        return rec;
    }

    public void setRec(RecSlotRec rec) {
        this.rec = rec;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
