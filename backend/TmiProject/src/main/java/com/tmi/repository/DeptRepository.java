package com.tmi.repository;

import com.tmi.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Department, Integer> {
}
