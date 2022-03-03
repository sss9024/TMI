package com.tmi.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "test")
public class Test {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK
    @ManyToOne
    @JsonIgnore
    private Report report;

    private String name; // ex) org.springframework.samples.petclinic.owner.VisitControllerTests
    private String type;
    private String errorType;

    @Column(length = 5000)
    private String errorMessage;
    private int elapsedTime;

    public Test() {
    }

    public Test(Test newTest) {
        this.id = newTest.getId();
        this.name = newTest.getName();
        this.type = newTest.getType();
        this.errorType = newTest.getErrorType();
        this.errorMessage = newTest.getErrorMessage();
        this.elapsedTime = newTest.getElapsedTime();
    }

    // txt에 대한 property만 가져옴. xml 파일의 정보도 추가 고려해야함.
}
