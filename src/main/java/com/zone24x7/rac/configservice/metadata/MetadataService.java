package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zone24x7.rac.configservice.util.Strings.SUCCESS;

/**
 * Service class relating to metadata.
 *
 */
@Service
public class MetadataService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ModelMapper modelMapper;

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

        return new CSResponse(SUCCESS, "CS-6002: Channel added successfully");
    }

    /**
     * Get all channels.
     *
     * @return Channel list DTO
     */
    ChannelListDTO getAllChannels() {
        List<MetadataDTO> channels = new ArrayList<>();

        List<Channel> channelList = channelRepository.findAll();

        for (Channel channel : channelList) {
            MetadataDTO metadataDTO = modelMapper.map(channel, MetadataDTO.class);
            channels.add(metadataDTO);
        }

        ChannelListDTO channelListDTO = new ChannelListDTO();
        channelListDTO.setChannels(channels);

        return channelListDTO;
    }

    /**
     * Add channel.
     *
     * @param page Page
     * @return     CS Response
     * @throws ValidationException Validation exception to throw
     */
    CSResponse addPage(Page page) throws ValidationException {

        // Validate whether page name exists in request.
        if (page.getName() == null) {
            logger.error("Page name field is missing");
            throw new ValidationException("CS-6003: Page name field is missing");
        }

        // Retrieve page from DB.
        Page existingPage = pageRepository.findByNameIgnoreCase(page.getName());

        // Validate whether page name exists in DB.
        if (existingPage != null) {
            logger.error("Page name already in use");
            throw new ValidationException("CS-6004: Page name already in use");

        } else {

            // Save page.
            pageRepository.save(page);

            logger.info("Page added successfully");
        }

        return new CSResponse(SUCCESS, "CS-6005: Page added successfully");
    }
}
