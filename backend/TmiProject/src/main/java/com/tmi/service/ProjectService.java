package com.tmi.service;

import com.tmi.controller.project.ProjectNotFoundException;
import com.tmi.entity.Project;
import com.tmi.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProject();

    Project postNewProject(Project newProject, int did);

    Project getProjectById(Long id);

    void putProject(Project newProject, Long id);

    void deleteProject(Long id);
}
