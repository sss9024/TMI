package com.tmi.repository;

import com.tmi.entity.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage, Long> {
    @Query(value = "select * from coverage where report_id = :id order by id desc", nativeQuery = true)
    List<Coverage> readAllCoveragesInReport(long id);
}
