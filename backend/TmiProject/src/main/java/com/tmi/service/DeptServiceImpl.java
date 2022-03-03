package com.tmi.service;

import com.tmi.entity.Department;
import com.tmi.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptRepository repo;

    @Override
    public List<Department> getAllDept() {
        return repo.findAll();
    }

    @Override
    public void postDepartment(Department dept) {
        repo.save(dept);
    }

    @Override
    public Department getDeptById(int did) {
        if(repo.findById(did).isPresent()){
            return repo.findById(did).get();
        }
        return null;
    }

    @Override
    public boolean deleteDepartment(int did) {
        if(repo.findById(did).isPresent()){
            repo.deleteById(did);
            return true;
        }
        return false;
    }
}
