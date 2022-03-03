package com.tmi.configuration;

import com.tmi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringJpa {

    private final ProjectRepository projectRepository;

    @Autowired
    public SpringJpa(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

}
