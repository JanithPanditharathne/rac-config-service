package com.zone24x7.rac.configservice.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MetadataService {

    @Autowired
    private MetadataRepository metadataRepository;

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

}
