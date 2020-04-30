package com.zone24x7.rac.configservice.recengine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for RecEngine.
 *
 */
public interface RecEngineRepository extends JpaRepository<RecEngine, Integer>, CrudRepository<RecEngine, Integer> {

    /**
     * Get rec engine model by key.
     *
     * @param configType Config type
     * @return           RecEngine
     */
    RecEngine findByConfigType(String configType);
}
