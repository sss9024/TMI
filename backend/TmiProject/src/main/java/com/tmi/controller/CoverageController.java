package com.tmi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmi.entity.Coverage;
import com.tmi.service.CoverageService;

@CrossOrigin("*")
@RestController
@RequestMapping("/coverage")
public class CoverageController {
    @Autowired
    private CoverageService coverageService;


    @GetMapping
    List<Coverage> all() {
        return coverageService.readAllCoverages();
    }

    @GetMapping("/{id}")
    Coverage findCoverageById(@PathVariable long id) {
        return coverageService.findCoverageById(id);
    }

    @GetMapping("/report/{id}")
    List<Coverage> getCoverageListByReportId(@PathVariable Long id) {
        return coverageService.readAllCoveragesInReport(id);
    }

//    @PostMapping
//    Coverage createCoverage(@RequestBody CoveragePostDto dto) {
//        return coverageService.createCoverage(dto);
//    }
}
