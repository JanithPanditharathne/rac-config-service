package com.zone24x7.rac.configservice.recengine.recslot;

import java.util.List;

public class RecEngineRecSlot {

    private String channel;
    private String page;
    private String placeholder;
    private List<Integer> ruleIds;
    private List<Integer> recIds;


    public RecEngineRecSlot() {
    }

    public RecEngineRecSlot(String channel, String page, String placeholder, List<Integer> ruleIds, List<Integer> recIds) {
        this.channel = channel;
        this.page = page;
        this.placeholder = placeholder;
        this.ruleIds = ruleIds;
        this.recIds = recIds;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public List<Integer> getRuleIds() {
        return ruleIds;
    }

    public void setRuleIds(List<Integer> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<Integer> getRecIds() {
        return recIds;
    }

    public void setRecIds(List<Integer> recIds) {
        this.recIds = recIds;
    }
}
