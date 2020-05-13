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

import static com.zone24x7.rac.configservice.util.Strings.*;

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
    private PlaceholderRepository placeholderRepository;

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
            throw new ValidationException(CHANNEL_NAME_FIELD_MISSING);
        }

        // Retrieve channel from DB.
        Channel existingChannel = channelRepository.findByNameIgnoreCase(channel.getName());

        // Validate whether channel name exists in DB.
        if (existingChannel != null) {
            logger.error("Channel name already in use");
            throw new ValidationException(CHANNEL_NAME_IN_USE);

        } else {

            // Save channel
            channelRepository.save(channel);

            logger.info("Channel added successfully");
        }

        return new CSResponse(SUCCESS, CHANNEL_ADDED_SUCCESSFULLY);
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
            throw new ValidationException(PAGE_NAME_FIELD_MISSING);
        }

        // Retrieve page from DB.
        Page existingPage = pageRepository.findByNameIgnoreCase(page.getName());

        // Validate whether page name exists in DB.
        if (existingPage != null) {
            logger.error("Page name already in use");
            throw new ValidationException(PAGE_NAME_IN_USE);

        } else {

            // Save page.
            pageRepository.save(page);

            logger.info("Page added successfully");
        }

        return new CSResponse(SUCCESS, PAGE_ADDED_SUCCESSFULLY);
    }

    /**
     * Get all pages.
     *
     * @return Page list DTO
     */
    PageListDTO getAllPages() {
        List<MetadataDTO> pages = new ArrayList<>();

        List<Page> pageList = pageRepository.findAll();

        for (Page page : pageList) {
            MetadataDTO metadataDTO = modelMapper.map(page, MetadataDTO.class);
            pages.add(metadataDTO);
        }

        PageListDTO pageListDTO = new PageListDTO();
        pageListDTO.setPages(pages);

        return pageListDTO;
    }

    /**
     * Add placeholder.
     *
     * @param placeholder Placeholder
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
    CSResponse addPlaceholder(Placeholder placeholder) throws ValidationException {

        // Validate whether placeholder name exists in request.
        if (placeholder.getName() == null) {
            logger.error("Placeholder name field is missing");
            throw new ValidationException(PLACEHOLDER_NAME_FIELD_MISSING);
        }

        // Retrieve placeholder from DB.
        Placeholder existingPlaceholder = placeholderRepository.findByNameIgnoreCase(placeholder.getName());

        // Validate whether placeholder name exists in DB.
        if (existingPlaceholder != null) {
            logger.error("Placeholder name already in use");
            throw new ValidationException(PLACEHOLDER_NAME_IN_USE);

        } else {

            // Save placeholder.
            placeholderRepository.save(placeholder);

            logger.info("Placeholder added successfully");
        }

        return new CSResponse(SUCCESS, PLACEHOLDER_ADDED_SUCCESSFULLY);
    }

    /**
     * Get all placeholders.
     *
     * @return Placeholder list DTO
     */
    PlaceholderListDTO getAllPlaceholders() {
        List<MetadataDTO> placeholders = new ArrayList<>();

        List<Placeholder> placeholderList = placeholderRepository.findAll();

        for (Placeholder placeholder : placeholderList) {
            MetadataDTO metadataDTO = modelMapper.map(placeholder, MetadataDTO.class);
            placeholders.add(metadataDTO);
        }

        PlaceholderListDTO placeholderListDTO = new PlaceholderListDTO();
        placeholderListDTO.setPlaceholders(placeholders);

        return placeholderListDTO;
    }
}
