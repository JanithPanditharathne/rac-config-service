package com.zone24x7.rac.configservice.bundle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for bundle algorithm.
 *
 */
public interface BundleAlgorithmRepository extends JpaRepository<BundleAlgorithm, Integer> {

    List<BundleAlgorithm> findAllByBundleID(int bundleID);

    List<BundleAlgorithm> findAllByAlgorithmID(int algorithmID);
}
