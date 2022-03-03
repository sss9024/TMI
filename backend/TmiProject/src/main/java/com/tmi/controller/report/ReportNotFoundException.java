package com.tmi.controller.report;

public class ReportNotFoundException extends RuntimeException {
    ReportNotFoundException(Long id) {
        super("Could not find test" + id);
    }
}
