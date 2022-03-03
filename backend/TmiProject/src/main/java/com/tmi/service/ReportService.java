package com.tmi.service;

import com.tmi.dto.ReportPostDto;
import com.tmi.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> getAllReport();

    Report findReportById(long id);

    List<Report> readAllReportsInApp(String aid);

    Report createReport(Report report, String appId);
}
