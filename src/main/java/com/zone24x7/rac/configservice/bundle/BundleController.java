package com.zone24x7.rac.configservice.bundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BundleController {

    @Autowired
    private BundleService bundleService;

    /**
     * Get all bundles.
     *
     * @return All bundles
     */
    @GetMapping("/bundles")
    public BundleSummaryListDTO getAllBundles() {
        return bundleService.getAllBundles();
    }
}
