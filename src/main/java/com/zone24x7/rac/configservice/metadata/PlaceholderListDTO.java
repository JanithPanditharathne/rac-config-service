package com.zone24x7.rac.configservice.metadata;

import java.util.List;

/**
 * Class representing a placeholder list DTO.
 *
 */
public class PlaceholderListDTO {

    private List<MetadataDTO> placeholders;

    public List<MetadataDTO> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<MetadataDTO> placeholders) {
        this.placeholders = placeholders;
    }
}
