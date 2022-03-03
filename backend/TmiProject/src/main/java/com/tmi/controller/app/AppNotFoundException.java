package com.tmi.controller.app;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(String id) {
        super("Could not find App " + id);
    }
}