package com.tmi.service;

import com.tmi.entity.Department;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DeptService {
    List<Department> getAllDept();
    void postDepartment(Department dept);
    Department getDeptById(int did);
    boolean deleteDepartment(int did);
}
