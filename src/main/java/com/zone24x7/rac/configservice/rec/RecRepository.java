package com.zone24x7.rac.configservice.rec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecRepository extends JpaRepository<Rec, Integer> {
    List<Rec> findAllByOrderByIdDesc();
}
