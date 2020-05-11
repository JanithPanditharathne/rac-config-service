package com.zone24x7.rac.configservice.metadata;

import java.util.List;

/**
 * Class representing a channel list DTO.
 *
 */
public class ChannelListDTO {

    private List<ChannelDTO> channels;

    public List<ChannelDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelDTO> channels) {
        this.channels = channels;
    }
}
