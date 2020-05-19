package com.zone24x7.rac.configservice.metadata.placeholder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for placeholder.
 *
 */
@Repository
public interface PlaceholderRepository extends JpaRepository<Placeholder, Integer> {

    /**
     * Get placeholder by name.
     *
     * @param name Placeholder name
     * @return     Placeholder
     */
    Placeholder findByNameIgnoreCase(String name);
}
