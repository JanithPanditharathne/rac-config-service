package com.zone24x7.rac.configservice.bundle;

import java.util.List;

public class BundleList {

    private List<Bundle> bundles;

    public BundleList() {
    }

    public BundleList(List<Bundle> bundles) {
        this.bundles = bundles;
    }

    public List<Bundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<Bundle> bundles) {
        this.bundles = bundles;
    }
}
