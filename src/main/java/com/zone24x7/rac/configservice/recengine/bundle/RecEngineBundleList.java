package com.zone24x7.rac.configservice.recengine.bundle;

import java.util.List;

public class RecEngineBundleList {

    private List<RecEngineBundle> bundles;

    public RecEngineBundleList() {
    }

    public RecEngineBundleList(List<RecEngineBundle> bundles) {
        this.bundles = bundles;
    }

    public List<RecEngineBundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<RecEngineBundle> bundles) {
        this.bundles = bundles;
    }
}
