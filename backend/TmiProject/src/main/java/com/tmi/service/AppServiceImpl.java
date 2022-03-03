package com.tmi.service;

import com.tmi.controller.app.AppDuplicatedException;
import com.tmi.controller.app.AppNotFoundException;
import com.tmi.entity.App;
import com.tmi.entity.Project;
import com.tmi.repository.AppRepository;
import com.tmi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppServiceImpl implements AppService{
    @Autowired
    private AppRepository repo;

    @Autowired
    private ProjectRepository projectRepository;

    public List<App> getAllApp() {
        return repo.findAll();
    }

    public List<App> getAppListByProjectId(Long pid) {
        return repo.findAllByProjectIdEquals(pid);
    }

    public boolean deleteAppById(String id) {
        repo.deleteById(id);
        return false;
    }

    public App postAppAtProject(App app, long id) {
        Project project = projectRepository.findById(id).get();
        if(repo.findApp(app.getTitle(), app.getGitUrl()) != null) {
            throw new AppDuplicatedException(app.getTitle(), app.getGitUrl());
        }
        app.setId(UUID.randomUUID().toString());
        app.setProject(project);
        app.setRegDate(new Date());

        return repo.save(app);
    }

    public void putAppData(App app) {
        repo.findById(app.getId()).ifPresent(selected -> {
            selected.setDescription(app.getDescription());
            repo.save(selected);
        });

    }

    @Override
    public App findById(String id) {
        Optional<App> app = repo.findById(id);

        if(app.isPresent()){
            return app.get();
        }else{
            return null;
        }
    }
}
