package com.tmi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<App> apps = new ArrayList<>();

    private String title;
    private String description;
    private Date regDate;

    @ManyToOne
    @JsonIgnore
    private Department department;
//    private String javaVersion; // java.version
//    private String branch; // git.branch
//    private String commitId; // git.commit.id (e2fbc561309d03d92a0958f3cf59219b1fc0d985)
//    private String commitUserName; // git.commit.user.name
    // private String commitMessage; //git.commit.message.short

}
