package com.zone24x7.rac.configservice.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Get metadata list of the given type.
     *
     * @param type metadata type (brands, departments...etc).
     * @return metadata list.
     */
    @GetMapping(path = "/metadata/{type}", produces= MediaType.APPLICATION_JSON_VALUE)
    public String getMetadata(@PathVariable String type) throws JsonProcessingException {
        MetadataList metadataList = metadataService.getMetadata(type);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(metadataList).replace("metadata", type);
    }


    /**
     * Add given metadata to the db.
     *
     * @param type metadata type (brands, departments...etc).
     * @param metadata metadata details.
     * @return status response.
     * @throws ValidationException if validation failed.
     */
    @PostMapping("/metadata/{type}")
    public CSResponse addMetadata(@PathVariable String type, MetadataDTO metadata) throws ValidationException {
        metadata.setType(type);
        return metadataService.addMetadata(modelMapper.map(metadata, Metadata.class));
    }


}
