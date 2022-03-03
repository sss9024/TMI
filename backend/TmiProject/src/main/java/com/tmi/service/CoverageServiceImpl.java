package com.tmi.service;

import com.tmi.dto.CoveragePostDto;
import com.tmi.entity.Coverage;
import com.tmi.entity.Report;
import com.tmi.repository.CoverageRepository;
import com.tmi.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoverageServiceImpl implements CoverageService {
    @Autowired
    private CoverageRepository coverageRepository;

    @Autowired
    private ReportRepository reportRepository;

    public List<Coverage> readAllCoverages() {
        return coverageRepository.findAll();
    }

    public List<Coverage> readAllCoveragesInReport(long id) {
        List<Coverage> coverages = coverageRepository.readAllCoveragesInReport(id);
        return coverages;
    }

    public Coverage findCoverageById(long id) {
        if(coverageRepository.findById(id).isPresent()){
            return coverageRepository.findById(id).get();
        }
        return null;
    }

    public Coverage createCoverage(CoveragePostDto coveragePostDto) {
        System.out.println("coveragePostDto = " + coveragePostDto);
        Optional<Report> report = reportRepository.findById(coveragePostDto.getReportId());
        Coverage coverage = coveragePostDto.toEntity();
        System.out.println("coverage = " + coverage);
        coverage.setReport(report.get());
        // coverage.setGroupName(coveragePostDto.getGroupName());
        // coverage.setPackageName(coveragePostDto.getPackageName());
        // coverage.setClassName(coveragePostDto.getClassName());
        // coverage.setLineCovMissed(coveragePostDto.getLineCovMissed());
        // coverage.setLineCovCovered(coveragePostDto.getLineCovCovered());
        // coverage.setBranchCovMissed(coveragePostDto.getBranchCovMissed());
        // coverage.setBranchCovCovered(coveragePostDto.getBranchCovCovered());
        // coverage.setHighlightHtml(coveragePostDto.getHighlightHtml());
        return coverageRepository.save(coverage);
    }

    public Coverage saveCoverage(Coverage coverage) {
    	return coverageRepository.save(coverage);
    }
}
