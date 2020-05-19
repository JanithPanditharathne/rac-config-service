package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.exception.ServerException;
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
    public BundleList getAllBundles() {
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
    public BundleDetails getBundle(@PathVariable int id) throws ValidationException {
        return bundleService.getBundle(id);
    }

    /**
     * Add new bundle.
     *
     * @param bundleDetails Bundle details to add
     * @return              CS Response
     * @throws ValidationException Validation exception to throw
     * @throws ServerException     Server exception to throw
     */
    @PostMapping("/bundles")
    public CSResponse addBundle(@RequestBody BundleDetails bundleDetails) throws ValidationException, ServerException {
        return bundleService.addBundle(bundleDetails);
    }

    /**
     * Edit bundle.
     *
     * @param id            Bundle ID
     * @param bundleDetails Bundle details to edit
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    @PutMapping("/bundles/{id}")
    public CSResponse editBundle(@PathVariable int id, @RequestBody BundleDetails bundleDetails) throws ValidationException, ServerException {
        return bundleService.editBundle(id, bundleDetails);
    }

    /**
     * Delete bundle.
     *
     * @param id Bundle ID
     * @return   CS Response
     * @throws ValidationException Exception to throw
     * @throws ServerException     Service exception to throw
     */
    @DeleteMapping("/bundles/{id}")
    public CSResponse deleteBundle(@PathVariable int id) throws ValidationException, ServerException {
        return bundleService.deleteBundle(id);
    }
}
