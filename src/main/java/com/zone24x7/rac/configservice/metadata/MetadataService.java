package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MetadataService {

//    @Autowired
//    private ChannelRepository channelRepository;
//
//    @Autowired
//    private PageRepository pageRepository;
//
//    @Autowired
//    private PlaceholderRepository placeholderRepository;

    @Autowired
    private MetadataRepository metadataRepository;





    /**
     * Get all channels.
     *
     * @return Channel list DTO
     */
//    ChannelList getAllChannels() {
//        List<Channel> channels = new ArrayList<>(channelRepository.findAll());
//        return new ChannelList(channels);
//    }

    /**
     * Get all pages.
     *
     * @return Page list DTO
     */
//    PageList getAllPages() {
//        List<Page> pages = new ArrayList<>(pageRepository.findAll());
//        return new PageList(pages);
//    }

    /**
     * Get all placeholders.
     *
     * @return Placeholder list DTO
     */
//    PlaceholderList getAllPlaceholders() {
//        List<Placeholder> placeholders = new ArrayList<>(placeholderRepository.findAll());
//        return new PlaceholderList(placeholders);
//    }


    /**
     * Get metadata list of the given type.
     *
     * @param type metadata type (brands, departments...etc).
     * @return metadata list.
     */
    MetadataList getMetadata(String type) {
        List<Metadata> metadata = metadataRepository.findAllByType(type);
        return new MetadataList(metadata);
    }












    /**
     * Add channel.
     *
     * @param channel Channel
     * @return        CS Response
     * @throws ValidationException Validation exception to throw
     */
//    CSResponse addChannel(Channel channel) throws ValidationException {
//
//        // Validate channel name for null.
//        if (channel.getName() == null) {
//            throw new ValidationException(Strings.CHANNEL_NAME_CANNOT_BE_NULL);
//        }
//
//        // Validate channel name for empty.
//        if (channel.getName().isEmpty()) {
//            throw new ValidationException(Strings.CHANNEL_NAME_CANNOT_BE_EMPTY);
//        }
//
//        // Retrieve channel from DB.
//        Channel existingChannel = channelRepository.findByNameIgnoreCase(channel.getName());
//
//        // Validate whether channel name exists in DB.
//        if (existingChannel != null) {
//            throw new ValidationException(Strings.CHANNEL_NAME_ALREADY_EXISTS);
//        }
//
//        // Save channel.
//        channelRepository.save(channel);
//
//        // Return status response.
//        return new CSResponse(Strings.SUCCESS, Strings.CHANNEL_ADDED_SUCCESSFULLY);
//    }


    /**
     * Add page.
     *
     * @param page Page
     * @return     CS Response
     * @throws ValidationException Validation exception to throw
     */
//    CSResponse addPage(Page page) throws ValidationException {
//
//        // Validate page name for null.
//        if (page.getName() == null) {
//            throw new ValidationException(Strings.PAGE_NAME_CANNOT_BE_NULL);
//        }
//
//        // Validate page name for empty.
//        if (page.getName().isEmpty()) {
//            throw new ValidationException(Strings.PAGE_NAME_CANNOT_BE_EMPTY);
//        }
//
//        // Retrieve page from DB.
//        Page existingPage = pageRepository.findByNameIgnoreCase(page.getName());
//
//        // Validate whether page name exists in DB.
//        if (existingPage != null) {
//            throw new ValidationException(Strings.PAGE_NAME_ALREADY_EXISTS);
//        }
//
//        // Save page.
//        pageRepository.save(page);
//
//        // Return status response.
//        return new CSResponse(Strings.SUCCESS, Strings.PAGE_ADDED_SUCCESSFULLY);
//    }


    /**
     * Add placeholder.
     *
     * @param placeholder Placeholder
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
//    CSResponse addPlaceholder(Placeholder placeholder) throws ValidationException {
//
//        // Validate placeholder name for null.
//        if (placeholder.getName() == null) {
//            throw new ValidationException(Strings.PLACEHOLDER_NAME_CANNOT_BE_NULL);
//        }
//
//        // Validate placeholder name for empty.
//        if (placeholder.getName().isEmpty()) {
//            throw new ValidationException(Strings.PLACEHOLDER_NAME_CANNOT_BE_EMPTY);
//        }
//
//        // Retrieve placeholder from DB.
//        Placeholder existingPlaceholder = placeholderRepository.findByNameIgnoreCase(placeholder.getName());
//
//        // Validate whether placeholder name exists in DB.
//        if (existingPlaceholder != null) {
//            throw new ValidationException(Strings.PLACEHOLDER_NAME_ALREADY_EXISTS);
//        }
//
//        // Save placeholder.
//        placeholderRepository.save(placeholder);
//
//        return new CSResponse(Strings.SUCCESS, Strings.PLACEHOLDER_ADDED_SUCCESSFULLY);
//    }


}
