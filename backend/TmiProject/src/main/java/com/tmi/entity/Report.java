package com.tmi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "report")
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private App app;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Test> tests = new ArrayList<>();

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Coverage> coverages = new ArrayList<>();

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

    public Report(LocalDateTime datetime, int totalLineCovMissed, int totalLineCovCovered, int totalBranchCovMissed,
                  int totalBranchCovCovered, int totalRunCount, int totalFailCount, int totalSkipCount, int totalErrorCount,
                  int totalElapsedTime) {
        this.datetime = datetime;
        this.totalLineCovMissed = totalBranchCovMissed;
        this.totalLineCovCovered = totalLineCovCovered;
        this.totalBranchCovMissed = totalBranchCovMissed;
        this.totalBranchCovCovered = totalBranchCovCovered;
        this.totalRunCount = totalRunCount;
        this.totalFailCount = totalFailCount;
        this.totalSkipCount = totalSkipCount;
        this.totalErrorCount = totalErrorCount;
        this.totalElapsedTime = totalElapsedTime;
    }
}
