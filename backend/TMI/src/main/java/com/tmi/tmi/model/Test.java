package com.tmi.tmi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "test")
public class Test {
	@Id
	String _id;
	
	String buildTime;
	String packageName;
	String packageShortName;
	String gitUrl;
	String projectName;
	int runCount;
    int failCount;
    int errorCount;
    int skipCount;
    int elapsedTime;
    List<TestCase> testCaseList;
}