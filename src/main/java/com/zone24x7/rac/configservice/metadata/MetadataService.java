package com.zone24x7.rac.configservice.metadata;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
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
    public MetadataList getMetadata(String type) {
        List<Metadata> metadata = metadataRepository.findAllByType(type);
        return new MetadataList(metadata);
    }


    /**
     * Add given metadata to the db.
     *
     * @param metadata metadata.
     * @return status response.
     */
    public CSResponse addMetadata(Metadata metadata) throws ValidationException {

        // Validate metadata type.
        MetadataValidations.validateType(metadata.getType());

        // Validate metadata name.
        MetadataValidations.validateName(metadata.getName());

        // Find for similar metadata.
        List<Metadata> metadataList = metadataRepository.findByTypeAndName(metadata.getType(), metadata.getName());
        if (metadataList != null && !metadataList.isEmpty()) {
            throw new ValidationException(Strings.SIMILAR_METADATA_ALREADY_EXISTS);
        }

        // Save new metadata.
        metadataRepository.save(metadata);

        // Return status response.
        return new CSResponse(Strings.SUCCESS, Strings.METADATA_ADDED_SUCCESSFULLY);
    }

}
