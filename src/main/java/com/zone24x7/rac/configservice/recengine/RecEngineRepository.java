package com.zone24x7.rac.configservice.recengine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for RecEngine.
 *
 */
public interface RecEngineRepository extends JpaRepository<RecEngineModel, Integer>, CrudRepository<RecEngineModel, Integer> {

    /**
     * Get rec engine model by key.
     *
     * @param key Key
     * @return    RecEngineModel
     */
    RecEngineModel findByKey(String key);
}
