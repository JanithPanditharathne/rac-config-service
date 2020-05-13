package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for metadata.
 *
 */
@RestController
@RequestMapping("/v1")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Add channel.
     *
     * @param metadataDTO Channel DTO
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
    @PostMapping(path = "/metadata/channels", consumes = "application/json")
    public CSResponse addChannel(@RequestBody MetadataDTO metadataDTO) throws ValidationException {
        return metadataService.addChannel(modelMapper.map(metadataDTO, Channel.class));
    }

    /**
     * Get all channels.
     *
     * @return All channels
     */
    @GetMapping(path = "/metadata/channels", produces = "application/json")
    public ChannelListDTO getChannels() {
        return metadataService.getAllChannels();
    }

    /**
     * Add page.
     *
     * @param metadataDTO Channel DTO
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
    @PostMapping(path = "/metadata/pages", consumes = "application/json")
    public CSResponse addPage(@RequestBody MetadataDTO metadataDTO) throws ValidationException {
        return metadataService.addPage(modelMapper.map(metadataDTO, Page.class));
    }

    /**
     * Get all pages.
     *
     * @return All pages
     */
    @GetMapping(path = "/metadata/pages", produces = "application/json")
    public PageListDTO getPages() {
        return metadataService.getAllPages();
    }

    /**
     * Add placeholder.
     *
     * @param metadataDTO Placeholder DTO
     * @return            CS Response
     * @throws ValidationException Validation exception to throw
     */
    @PostMapping(path = "/metadata/placeholders", consumes = "application/json")
    public CSResponse addPlaceholder(@RequestBody MetadataDTO metadataDTO) throws ValidationException {
        return metadataService.addPlaceholder(modelMapper.map(metadataDTO, Placeholder.class));
    }
}
