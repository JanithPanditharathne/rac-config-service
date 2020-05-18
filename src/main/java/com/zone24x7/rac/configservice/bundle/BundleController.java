package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Add new bundle.
     *
     * @param bundleDetailDTO Bundle details to add
     * @return                CS Response
     * @throws ValidationException Exception to throw
     */
    @PostMapping("/bundles")
    public CSResponse addBundle(@RequestBody BundleDetailDTO bundleDetailDTO) throws ValidationException {
        return bundleService.addBundle(bundleDetailDTO);
    }
}
