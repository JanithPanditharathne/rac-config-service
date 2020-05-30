package com.zone24x7.rac.configservice.metadata;

import java.util.List;

public class MetadataList {

    private List<Metadata> metadata;

    public MetadataList() {
    }

    public MetadataList(List<Metadata> metadata) {
        this.metadata = metadata;
    }

    public List<Metadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }
}
