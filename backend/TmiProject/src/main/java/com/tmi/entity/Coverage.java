package com.tmi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Report report;

    private String groupName;
    private String packageName;
    private String className;
    private int lineCovMissed;
    private int lineCovCovered;
    private int branchCovMissed;
    private int branchCovCovered;
    private String highlightHtml;

    public Coverage(String groupName, String packageName, String className, int lineCovMissed, int lineCovCovered, int branchCovMissed, int branchCovCovered, String highlightHtml) {
        this.groupName = groupName;
        this.packageName = packageName;
        this.className = className;
        this.lineCovMissed = lineCovMissed;
        this.lineCovCovered = lineCovCovered;
        this.branchCovMissed = branchCovMissed;
        this.branchCovCovered = branchCovCovered;
        this.highlightHtml = highlightHtml;
    }
}
