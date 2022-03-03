package com.tmi.dto;

import java.time.LocalDateTime;

import com.tmi.entity.Report;
import lombok.Getter;

@Getter
public class ReportPostDto {
    private LocalDateTime datetime;
    private int totalLineCovMissed;
    private int totalLineCovCovered;
    private int totalBranchCovMissed;
    private int totalBranchCovCovered;
    private int totalRunCount;
    private int totalFailCount;
    private int totalSkipCount;
    private int totalErrorCount;
    private int totalElapsedTime;
    private String appId;

    public Report toEntity() {
        return new Report(datetime, totalLineCovMissed, totalLineCovCovered, totalBranchCovMissed,
                totalBranchCovCovered, totalRunCount, totalFailCount, totalSkipCount, totalErrorCount,
                totalElapsedTime);
    }
}
