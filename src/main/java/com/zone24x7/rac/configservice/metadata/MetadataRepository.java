package com.zone24x7.rac.configservice.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
    List<Metadata> findAllByType(String type);
    Metadata findByTypeAndId(String type, int id);
    List<Metadata> findByTypeAndName(String type, String name);

    @Query("SELECT DISTINCT m.type FROM Metadata m")
    List<String> findDistinctTypes();
}
