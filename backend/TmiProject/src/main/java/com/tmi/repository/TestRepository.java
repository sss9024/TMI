package com.tmi.repository;

import com.tmi.entity.Report;
import com.tmi.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByReport_Id(Long id);
}
