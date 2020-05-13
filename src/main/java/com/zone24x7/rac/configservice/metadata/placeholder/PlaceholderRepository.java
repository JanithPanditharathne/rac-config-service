package com.zone24x7.rac.configservice.metadata.placeholder;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for placeholder.
 *
 */
public interface PlaceholderRepository extends JpaRepository<Placeholder, Integer> {

    /**
     * Get placeholder by name.
     *
     * @param name Placeholder name
     * @return     Placeholder
     */
    Placeholder findByNameIgnoreCase(String name);
}
