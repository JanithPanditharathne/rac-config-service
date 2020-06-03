package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.metadata.Metadata;

import java.util.List;

public class RecSlotDetail {

    private int id;
    private Metadata channel;
    private Metadata page;
    private Metadata placeholder;
    private RecSlotRecDetail rec;
    private List<RecSlotRuleDetail> rules;

    public RecSlotDetail() {
    }

    public RecSlotDetail(int id, Metadata channel, Metadata page, Metadata placeholder, RecSlotRecDetail rec, List<RecSlotRuleDetail> rules) {
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

    public Metadata getChannel() {
        return channel;
    }

    public void setChannel(Metadata channel) {
        this.channel = channel;
    }

    public Metadata getPage() {
        return page;
    }

    public void setPage(Metadata page) {
        this.page = page;
    }

    public Metadata getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Metadata placeholder) {
        this.placeholder = placeholder;
    }

    public RecSlotRecDetail getRec() {
        return rec;
    }

    public void setRec(RecSlotRecDetail rec) {
        this.rec = rec;
    }

    public List<RecSlotRuleDetail> getRules() {
        return rules;
    }

    public void setRules(List<RecSlotRuleDetail> rules) {
        this.rules = rules;
    }
}
