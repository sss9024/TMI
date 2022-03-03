package com.tmi.raw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tmi.raw.entity.CoverageRawData;

public interface CoverageRawDataRepository extends MongoRepository<CoverageRawData, String>{
	
}