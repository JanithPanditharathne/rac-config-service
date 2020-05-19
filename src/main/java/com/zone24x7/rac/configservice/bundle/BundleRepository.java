package com.zone24x7.rac.configservice.bundle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for channel.
 *
 */
@Repository
public interface BundleRepository extends JpaRepository<Bundle, Integer> {
}
