package com.tmi.tmi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tmi.tmi.model.Test;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String>{
	List<Test> findByPackageShortNameAndBuildTimeAndProjectName(String packageShortName, String buildTime, String projectName);
}