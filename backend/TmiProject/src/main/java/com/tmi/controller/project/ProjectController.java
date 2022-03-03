package com.tmi.controller.project;

import com.tmi.entity.Project;
import com.tmi.entity.User;
import com.tmi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping()
    ResponseEntity<List<Project>> getAllProject() {
        return new ResponseEntity<>(service.getAllProject(), HttpStatus.OK);
    }

    @PostMapping("/{did}")
    ResponseEntity<Project> postNewProject(@RequestBody Project newProject, @PathVariable int did) {
        Project project = service.postNewProject(newProject, did);
        if(project == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    Project getProjectById(@PathVariable Long id) {
        Project project = service.getProjectById(id);
        if(project == null){
            return null;
        }else{
            return project;
        }
    }

    @PutMapping("/{id}")
    void putProject(@RequestBody Project newProject, @PathVariable Long id) {
        service.putProject(newProject, id);
    }

    @DeleteMapping("/{id}")
    void deleteProject(@PathVariable Long id) {
        service.deleteProject(id);
    }
}