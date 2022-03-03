package com.tmi.service;

import com.tmi.entity.Project;
import com.tmi.repository.DeptRepository;
import com.tmi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository repo;

    @Autowired
    private DeptRepository deptRepo;

    @Override
    public List<Project> getAllProject() {
        return repo.findAll();
    }

    @Override
    public Project postNewProject(Project newProject, int did) {
        deptRepo.findById(did).ifPresent(selected -> {
            newProject.setDepartment(selected);
        });
        newProject.setRegDate(new Date());
        return repo.save(newProject);
    }

    @Override
    public Project getProjectById(Long id) {
        if(repo.findById(id).isPresent()){
            return repo.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public void putProject(Project newProject, Long id) {
        repo.findById(id).ifPresent(selected -> {
            selected.setDescription(newProject.getDescription());
            selected.setDepartment(newProject.getDepartment());
            selected.setTitle(newProject.getTitle());
            selected.setApps(newProject.getApps());
            repo.save(selected);
        });
    }

    @Override
    public void deleteProject(Long id) {
        repo.deleteById(id);
    }
}
