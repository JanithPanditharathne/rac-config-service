package com.zone24x7.rac.configservice.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;


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


}
