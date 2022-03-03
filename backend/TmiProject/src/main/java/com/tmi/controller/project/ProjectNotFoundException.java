package com.tmi.controller.project;

public class ProjectNotFoundException extends RuntimeException {
    ProjectNotFoundException(Long id) {
        super("Could not find project " + id);
    }
}