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

    /**
     * Add channel.
     *
     * @param channelDTO Channel DTO
     * @return           CS Response
     * @throws ValidationException Validation exception to throw
     */
    @PostMapping(path = "/metadata/channels", consumes = "application/json")
    public CSResponse addChannel(@RequestBody ChannelDTO channelDTO) throws ValidationException {
        ModelMapper modelMapper = new ModelMapper();
        Channel channel = modelMapper.map(channelDTO, Channel.class);

        return metadataService.addChannel(channel);
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
}
