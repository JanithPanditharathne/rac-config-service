package com.zone24x7.rac.configservice.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for page.
 *
 */
public interface PageRepository extends JpaRepository<Page, Integer> {

    /**
     * Get page by name.
     *
     * @param name Page name
     * @return     Page
     */
    Page findByNameIgnoreCase(String name);
}
