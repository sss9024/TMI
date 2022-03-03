package com.tmi.controller.report;

import java.util.List;

import com.tmi.dto.ReportPostDto;
import com.tmi.entity.Report;
import com.tmi.service.ReportService;
import com.tmi.service.ReportServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService service;

    @GetMapping()
    List<Report> getAllReport() {
        return service.getAllReport();
    }

    @GetMapping("/{id}")
    Report findReportById(@PathVariable Long id) {
        return service.findReportById(id);
    }

    @GetMapping("/app/{aid}")
    List<Report> allReportByAppId(@PathVariable String aid) {
        return service.readAllReportsInApp(aid);
    }

    @PostMapping("/{aid}")
    Report createReport(@RequestBody Report report, @PathVariable String aid) {
        System.out.println(aid);
        return service.createReport(report, aid);
    }
}
