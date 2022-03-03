package com.tmi.raw.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tmi.raw.entity.TestRawData;

public interface TestRawDataRepository extends MongoRepository<TestRawData, String>{
	List<TestRawData> findByPackageShortNameAndBuildTimeAndProjectName(String packageShortName, String buildTime, String projectName);
}