package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ModelMapper modelMapper;



    /**
     * Get all channels.
     *
     * @return All channels
     */
//    @GetMapping("/metadata/channels")
//    public ChannelList getChannels() {
//        return metadataService.getAllChannels();
//    }


    /**
     * Get all pages.
     *
     * @return All pages
     */
//    @GetMapping("/metadata/pages")
//    public PageList getPages() {
//        return metadataService.getAllPages();
//    }


    /**
     * Get all placeholders.
     *
     * @return All placeholders
     */
//    @GetMapping("/metadata/placeholders")
//    public PlaceholderList getPlaceholders() {
//        return metadataService.getAllPlaceholders();
//    }


    /**
     * Get metadata list of the given type.
     *
     * @param type metadata type (brands, departments...etc).
     * @return metadata list.
     */
    @GetMapping("/metadata/{type}")
    public MetadataList getMetadata(@PathVariable String type) {
        return metadataService.getMetadata(type);
    }







    // TODO: Uncomment after the test

    /**
     * Add channel.
     *
     * @param channelDTO Channel DTO
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
//    @PostMapping("/metadata/channels")
//    public CSResponse addChannel(@RequestBody ChannelDTO channelDTO) throws ValidationException {
//        return metadataService.addChannel(modelMapper.map(channelDTO, Channel.class));
//    }


    /**
     * Add page.
     *
     * @param pageDTO page DTO
     * @return CS Response
     * @throws ValidationException Validation exception to throw
     */
//    @PostMapping("/metadata/pages")
//    public CSResponse addPage(@RequestBody PageDTO pageDTO) throws ValidationException {
//        return metadataService.addPage(modelMapper.map(pageDTO, Page.class));
//    }


    /**
     * Add placeholder.
     *
     * @param placeholderDTO Placeholder DTO
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
//    @PostMapping("/metadata/placeholders")
//    public CSResponse addPlaceholder(@RequestBody PlaceholderDTO placeholderDTO) throws ValidationException {
//        return metadataService.addPlaceholder(modelMapper.map(placeholderDTO, Placeholder.class));
//    }


}
