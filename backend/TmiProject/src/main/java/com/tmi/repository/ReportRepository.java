package com.tmi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tmi.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "select * from report  where app_id = :aid order by id desc", nativeQuery = true)
    List<Report> findAllByAppIdOrderByIdDesc(String aid);

    List<Report> findAllByOrderByIdDesc();
    
    List<Report> findByAppIdAndDatetime(Long aid, String datetime);
}
