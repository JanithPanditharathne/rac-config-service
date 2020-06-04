package com.zone24x7.rac.configservice.metadata;

import java.util.List;

public class MetadataList {

    private List<Metadata> metadataList;

    public MetadataList() {
    }

    public MetadataList(List<Metadata> metadataList) {
        this.metadataList = metadataList;
    }

    public List<Metadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<Metadata> metadataList) {
        this.metadataList = metadataList;
    }
}
