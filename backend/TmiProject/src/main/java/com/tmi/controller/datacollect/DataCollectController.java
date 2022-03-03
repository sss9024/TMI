package com.tmi.controller.datacollect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmi.service.DataCollectService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class DataCollectController {
	
	@Autowired
	DataCollectService dataCollectService;
	
	@PostMapping("/data")
	@ApiOperation("MongoDB 데이터 가져와서 저장하기")
	boolean dataCollect(String projectName, String gitUrl, String buildTime, String coverageKey, String [] testKeys) {
		System.out.println(dataCollectService.dataCollect(projectName, gitUrl, buildTime, coverageKey, testKeys));
		
		return true;
	}
}
