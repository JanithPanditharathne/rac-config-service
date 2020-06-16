package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Get metadata types.
     *
     * @return metadata type list.
     */
    @GetMapping(path = "/metadata/types")
    public List<String> getMetadataTypes() {
        return metadataService.getMetadataTypes();
    }


    /**
     * Get metadata list of the given type.
     *
     * @param type metadata type (brands, departments...etc).
     * @return metadata list.
     */
    @GetMapping(path = "/metadata/{type}")
    public MetadataList getMetadata(@PathVariable String type) {
        return metadataService.getMetadata(type);
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
    public CSResponse addMetadata(@PathVariable String type, @RequestBody MetadataDTO metadata) throws ValidationException {
        metadata.setType(type);
        return metadataService.addMetadata(modelMapper.map(metadata, Metadata.class));
    }


}
