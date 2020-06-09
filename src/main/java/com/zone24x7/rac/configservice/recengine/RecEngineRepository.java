package com.zone24x7.rac.configservice.recengine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for rec_engine.
 *
 */
@Repository
public interface RecEngineRepository extends JpaRepository<RecEngine, Integer> {

    /**
     * Get rec_engine model by key.
     *
     * @param configType Config type
     * @return rec_engine model
     */
    RecEngine findByConfigType(String configType);
}
