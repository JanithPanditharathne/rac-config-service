package com.zone24x7.rac.configservice.recslot;

import com.zone24x7.rac.configservice.metadata.channel.Channel;
import com.zone24x7.rac.configservice.metadata.page.Page;
import com.zone24x7.rac.configservice.metadata.placeholder.Placeholder;

import java.util.List;

public class RecSlotDetail {

    private int id;
    private Channel channel;
    private Page page;
    private Placeholder placeholder;
    private RecSlotRecDetail rec;
    private List<RecSlotRuleDetail> rules;

    public RecSlotDetail() {
    }

    public RecSlotDetail(int id, Channel channel, Page page, Placeholder placeholder, RecSlotRecDetail rec, List<RecSlotRuleDetail> rules) {
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
