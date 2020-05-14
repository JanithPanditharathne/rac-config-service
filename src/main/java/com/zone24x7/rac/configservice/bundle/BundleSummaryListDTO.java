package com.zone24x7.rac.configservice.bundle;

import java.util.List;

public class BundleSummaryListDTO {

    private List<BundleSummaryDTO> bundles;

    public BundleSummaryListDTO(List<BundleSummaryDTO> bundles) {
        this.bundles = bundles;
    }

    public List<BundleSummaryDTO> getBundles() {
        return bundles;
    }

    public void setBundles(List<BundleSummaryDTO> bundles) {
        this.bundles = bundles;
    }
}
