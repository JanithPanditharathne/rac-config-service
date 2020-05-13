package com.zone24x7.rac.configservice.metadata.page;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<Page, Integer> {

    /**
     * Get page by name.
     *
     * @param name Page name
     * @return     Page
     */
    Page findByNameIgnoreCase(String name);
}
