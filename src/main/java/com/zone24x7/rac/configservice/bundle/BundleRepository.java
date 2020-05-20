package com.zone24x7.rac.configservice.bundle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Integer> {
    List<Bundle> findAllByOrderByIdDesc();
}
