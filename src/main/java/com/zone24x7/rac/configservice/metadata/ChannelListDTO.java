package com.zone24x7.rac.configservice.metadata;

import java.util.List;

/**
 * Class representing a channel list DTO.
 *
 */
public class ChannelListDTO {

    private List<MetadataDTO> channels;

    public List<MetadataDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<MetadataDTO> channels) {
        this.channels = channels;
    }
}
