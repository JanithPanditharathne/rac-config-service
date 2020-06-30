package com.zone24x7.rac.configservice.bundle;

import com.zone24x7.rac.configservice.actiontrace.ActionTraceService;
import com.zone24x7.rac.configservice.exception.ServerException;
import com.zone24x7.rac.configservice.exception.ValidationException;
import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BundleController {

    @Autowired
    private BundleService bundleService;

    @Autowired
    private ActionTraceService actionTraceService;

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
    public BundleDetail getBundle(@PathVariable int id) throws ValidationException {
        actionTraceService.log();
        return bundleService.getBundle(id);
    }

    /**
     * Add new bundle.
     *
     * @param bundleDetail Bundle details to add
     * @return              CS Response
     * @throws ValidationException Validation exception to throw
     * @throws ServerException     Server exception to throw
     */
    @PostMapping("/bundles")
    public CSResponse addBundle(@RequestBody BundleDetail bundleDetail) throws ValidationException, ServerException {
        actionTraceService.log(bundleDetail);
        CSResponse csResponse = bundleService.addBundle(bundleDetail);
        actionTraceService.add(bundleDetail);
        return csResponse;
    }

    /**
     * Edit bundle.
     *
     * @param id            Bundle ID
     * @param bundleDetail Bundle details to edit
     * @return              CS Response
     * @throws ValidationException Exception to throw
     */
    @PutMapping("/bundles/{id}")
    public CSResponse editBundle(@PathVariable int id, @RequestBody BundleDetail bundleDetail) throws ValidationException, ServerException {
        actionTraceService.log(bundleDetail);
        CSResponse csResponse = bundleService.editBundle(id, bundleDetail);
        actionTraceService.add(bundleDetail);
        return csResponse;
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
        actionTraceService.log();
        CSResponse csResponse = bundleService.deleteBundle(id);
        actionTraceService.add();
        return csResponse;
    }
}
