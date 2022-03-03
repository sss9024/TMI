package com.tmi.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmi.dto.CoveragePostDto;
import com.tmi.entity.Coverage;
import com.tmi.entity.Report;
import com.tmi.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CoverageService {
    List<Coverage> readAllCoverages();

    List<Coverage> readAllCoveragesInReport(long id);

    Coverage findCoverageById(long id);

    Coverage createCoverage(CoveragePostDto coveragePostDto);
//=======
//    public List<Coverage> readAllCoveragesInReport(long id) {
//        Report report = reportRepository.findById(id).get();
//        List<Coverage> coverages = report.getCoverages();
//        coverages = coverages.stream().sorted(Comparator.comparing(Coverage::getId).reversed())
//                .collect(Collectors.toList());
//        return coverages;
//    }
//
//    public Optional<Coverage> readOneCoverage(long id) {
//        return coverageRepository.findById(id);
//    }
//
//    public Coverage createCoverage(CoveragePostDto coveragePostDto) {
//        System.out.println("coveragePostDto = " + coveragePostDto);
//        Optional<Report> report = reportRepository.findById(coveragePostDto.getReportId());
//        Coverage coverage = coveragePostDto.toEntity();
//        System.out.println("coverage = " + coverage);
//        coverage.setReport(report.get());
//        // coverage.setGroupName(coveragePostDto.getGroupName());
//        // coverage.setPackageName(coveragePostDto.getPackageName());
//        // coverage.setClassName(coveragePostDto.getClassName());
//        // coverage.setLineCovMissed(coveragePostDto.getLineCovMissed());
//        // coverage.setLineCovCovered(coveragePostDto.getLineCovCovered());
//        // coverage.setBranchCovMissed(coveragePostDto.getBranchCovMissed());
//        // coverage.setBranchCovCovered(coveragePostDto.getBranchCovCovered());
//        // coverage.setHighlightHtml(coveragePostDto.getHighlightHtml());
//        return coverageRepository.save(coverage);
//    }
//
    Coverage saveCoverage(Coverage coverage);
//>>>>>>> 246801c2e9b5a0dde2b447f59404c841c752f085
}
