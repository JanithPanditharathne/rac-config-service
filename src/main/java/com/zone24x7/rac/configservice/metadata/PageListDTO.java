package com.zone24x7.rac.configservice.metadata;

import java.util.List;

/**
 * Class representing a page list DTO.
 *
 */
public class PageListDTO {

    private List<MetadataDTO> pages;

    public List<MetadataDTO> getPages() {
        return pages;
    }

    public void setPages(List<MetadataDTO> pages) {
        this.pages = pages;
    }
}
