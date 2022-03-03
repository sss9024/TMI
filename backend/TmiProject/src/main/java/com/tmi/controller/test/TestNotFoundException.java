package com.tmi.controller.test;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(Long id) {
        super("Could not find test" + id);
    }
}