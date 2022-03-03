package com.tmi.controller.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppDuplicatedAdvice {
	@ResponseBody
    @ExceptionHandler(AppDuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String AppDuplicatedHandler(AppDuplicatedException ex) {
        return ex.getMessage();
    }
}
