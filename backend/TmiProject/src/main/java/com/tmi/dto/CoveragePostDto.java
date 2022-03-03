package com.tmi.dto;

import com.tmi.entity.Coverage;
import lombok.Getter;

@Getter
public class CoveragePostDto {
    private String groupName;
    private String packageName;
    private String className;
    private int lineCovMissed;
    private int lineCovCovered;
    private int branchCovMissed;
    private int branchCovCovered;
    private String highlightHtml;
    private long reportId;

    public Coverage toEntity() {
        return new Coverage(
                groupName,
                packageName,
                className,
                lineCovMissed,
                lineCovCovered,
                branchCovMissed,
                branchCovCovered,
                highlightHtml
        );
    }
}
