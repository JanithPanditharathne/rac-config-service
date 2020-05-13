package com.zone24x7.rac.configservice.metadata.channel;

import java.util.List;


public class ChannelList {

    private List<Channel> channels;

    public ChannelList(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
