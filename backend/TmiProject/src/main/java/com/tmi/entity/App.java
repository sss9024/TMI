package com.tmi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Data
@Table(name = "app")
@Getter
@Setter
@NoArgsConstructor
public class App {

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Project project;

    @Id
    private String id;
    private String title;
    private String description;
    private Date regDate;
    private String gitUrl;
    
    private LocalDateTime recentDatetime;
    private int recentTotalLineCovMissed;
    private int recentTotalLineCovCovered;
    private int recentTotalBranchCovMissed;
    private int recentTotalBranchCovCovered;
    private int recentTotalRunCount;
    private int recentTotalFailCount;
    private int recentTotalSkipCount;
    private int recentTotalErrorCount;
    private int recentTotalElapsedTime;
}
