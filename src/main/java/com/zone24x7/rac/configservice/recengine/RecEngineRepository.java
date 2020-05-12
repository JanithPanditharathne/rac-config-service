package com.zone24x7.rac.configservice.recengine;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for rec_engine.
 *
 */
public interface RecEngineRepository extends JpaRepository<RecEngine, Integer> {

    /**
     * Get rec_engine model by key.
     *
     * @param configType Config type
     * @return rec_engine model
     */
    RecEngine findByConfigType(String configType);
}
