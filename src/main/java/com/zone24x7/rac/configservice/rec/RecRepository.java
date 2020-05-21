package com.zone24x7.rac.configservice.rec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecRepository extends JpaRepository<Rec, Integer> {
}
