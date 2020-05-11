package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class relating to metadata.
 *
 */
@Service
public class MetadataService {

    @Autowired
    private ChannelRepository channelRepository;

    Logger logger = LoggerFactory.getLogger(MetadataService.class);

    /**
     * Add channel.
     *
     * @param channel Channel
     * @return        CS Response
     * @throws ValidationException Validation exception to throw
     */
    CSResponse addChannel(Channel channel) throws ValidationException {

        // Validate whether channel name exists in request.
        if (channel.getName() == null) {
            logger.error("Channel name field is missing");
            throw new ValidationException("CS-6000: Channel name field is missing");
        }

        // Retrieve channel from DB.
        Channel existingChannel = channelRepository.findByNameIgnoreCase(channel.getName());

        // Validate whether channel name exists in DB.
        if (existingChannel != null) {
            logger.error("Channel name already in use");
            throw new ValidationException("CS-6001: Channel name already in use");

        } else {

            // Save channel
            channelRepository.save(channel);

            logger.info("Channel added successfully");
        }

        return new CSResponse("SUCCESS", "CS-6002: Channel added successfully");
    }

    /**
     * Get all channels.
     *
     * @return Channel list DTO
     */
    ChannelListDTO getAllChannels() {
        List<ChannelDTO> channels = new ArrayList<>();

        List<Channel> channelList = channelRepository.findAll();

        for (Channel channel : channelList) {
            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(channel.getId());
            channelDTO.setName(channel.getName());

            channels.add(channelDTO);
        }

        ChannelListDTO channelListDTO = new ChannelListDTO();
        channelListDTO.setChannels(channels);

        return channelListDTO;
    }
}
