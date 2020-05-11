package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param channel Channel
     * @return        CS Response
     * @throws ValidationException Validation exception to throw
     */
    @RequestMapping(path = "/metadata/channels", method = RequestMethod.POST, consumes = "application/json")
    public CSResponse addChannel(@RequestBody Channel channel) throws ValidationException {
        return metadataService.addChannel(channel);
    }
}
