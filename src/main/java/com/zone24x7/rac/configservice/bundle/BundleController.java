package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * Get bundle detail by id.
     *
     * @param id Bundle ID
     * @return   Bundle details
     * @throws ValidationException Exception to throw
     */
    @GetMapping("/bundles/{id}")
    public BundleDetailDTO getBundle(@PathVariable int id) throws ValidationException {
        return bundleService.getBundle(id);
    }
}
