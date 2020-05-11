package com.zone24x7.rac.configservice.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for channel.
 *
 */
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    /**
     * Get channel by name.
     *
     * @param name Channel name
     * @return     Channel
     */
    Channel findByNameIgnoreCase(String name);
}
