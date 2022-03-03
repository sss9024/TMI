package com.tmi.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmi.entity.App;
import com.tmi.entity.Coverage;
import com.tmi.entity.Report;
import com.tmi.entity.Test;
import com.tmi.raw.entity.CoverageRawData;
import com.tmi.raw.entity.Package;
import com.tmi.raw.entity.Sourcefile;
import com.tmi.raw.entity.TestCase;
import com.tmi.raw.entity.TestRawData;
import com.tmi.raw.repository.CoverageRawDataRepository;
import com.tmi.raw.repository.TestRawDataRepository;
import com.tmi.repository.AppRepository;
import com.tmi.repository.CoverageRepository;
import com.tmi.repository.ReportRepository;
import com.tmi.repository.TestRepository;

@Service
public class DataCollectService {
	@Autowired
	CoverageRawDataRepository coverageRawDataRepository;
	@Autowired
	TestRawDataRepository testRawDataRepository;
	@Autowired
	ReportRepository reportRepository;
	@Autowired
	AppRepository appRepository;
	@Autowired
	CoverageRepository coverageRepository;
	@Autowired
	TestRepository testRepository;
	
	
	public Optional<CoverageRawData> getCoverageData(String id) {
		return coverageRawDataRepository.findById(id);
	}
	
	public Optional<TestRawData> getTestData(String id) {
		return testRawDataRepository.findById(id);
	}
	
	public App getAppData(String gitUrl, String projectName) {
		//return appRepository.findById(projectName + "_" + encryptImpl.encrypt(gitUrl));
		return appRepository.findApp(projectName, gitUrl);
	}
	
	public String dataCollect(String projectName, String gitUrl, String buildTime, String coverageKey, String [] testKeys) {
		App app = getAppData(gitUrl, projectName);
//		if(appData.get() == null) {
//			return "This is not registered app.";
//		}
		//String appId = appData.get().getId();
		
		
		//빌드 시간 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.parse(buildTime, formatter);
        
        CoverageRawData coverageRawData = getCoverageData(coverageKey).get();
        int totalLineCovCovered = coverageRawData.getLine().getCovered();
        int totalLineCovMissed = coverageRawData.getLine().getMissed();
        int totalBranchCovCovered = coverageRawData.getBranch().getCovered();
        int totalBranchCovMissed = coverageRawData.getBranch().getMissed();
        
        Report report = new Report(datetime, totalLineCovMissed, totalLineCovCovered, totalBranchCovMissed, totalBranchCovCovered, 0, 0, 0, 0, 0);
		
		report.setApp(app);
		
		reportRepository.save(report);
		
		int totalElapsedTime = 0;
		int totalErrorCount = 0;
		int totalFailCount = 0;
		int totalRunCount = 0;
		int totalSkipCount = 0;
		//Coverage test 연결용
		//Long reportId = reportRepository.save(report).getId();
		
		for(Package pkg : coverageRawData.getPackageList()) {
			for(Sourcefile sourcefile : pkg.getSourceFileList()) {
				Coverage coverage = new Coverage();
				coverage.setClassName(sourcefile.getName());
				coverage.setPackageName(pkg.getName());
				coverage.setReport(report);
				coverage.setGroupName(projectName);
				if(sourcefile.getBranch() == null) {
					coverage.setBranchCovCovered(0);
					coverage.setBranchCovMissed(0);
				}else {
					coverage.setBranchCovCovered(sourcefile.getBranch().getCovered());
					coverage.setBranchCovMissed(sourcefile.getBranch().getMissed());
				}
				
				if(sourcefile.getLine() == null) {
					coverage.setLineCovCovered(0);
					coverage.setLineCovMissed(0);
				}else {
					coverage.setLineCovCovered(sourcefile.getLine().getCovered());
					coverage.setLineCovMissed(sourcefile.getLine().getMissed());
				}
				
				coverageRepository.save(coverage);
			}
		}
		
		for(int i=0;i<testKeys.length;i++) {
			TestRawData testRawData = getTestData(testKeys[i]).get();
			
			for(TestCase testcase : testRawData.getTestCaseList()) {
				Test test = new Test();
				test.setReport(report);
				test.setName(testcase.getTestCaseName());
				test.setElapsedTime(testcase.getTestTime());
				totalElapsedTime += testcase.getTestTime();
				if(testcase.isFail() && !testcase.getFailDescription().equals("skipped")) {
					test.setType("fail");
					totalFailCount++;
					test.setErrorMessage(testcase.getFailDescription());
				}else if(testcase.getFailDescription() != null && testcase.getFailDescription().equals("skipped")) {
					test.setType("skip");
					totalSkipCount++;
					test.setErrorMessage("skipped");
				}else {
					totalRunCount++;
					test.setType("pass");
				}
				
				testRepository.save(test);
			}
			
		}
		report.setTotalElapsedTime(totalElapsedTime);
		report.setTotalErrorCount(totalErrorCount);
		report.setTotalFailCount(totalFailCount);
		report.setTotalRunCount(totalRunCount);
		report.setTotalSkipCount(totalSkipCount);
		
		app.setRecentDatetime(datetime);
		app.setRecentTotalBranchCovCovered(totalBranchCovCovered);
		app.setRecentTotalBranchCovMissed(totalBranchCovMissed);
		app.setRecentTotalElapsedTime(totalElapsedTime);
		app.setRecentTotalLineCovCovered(totalLineCovCovered);
		app.setRecentTotalLineCovMissed(totalLineCovMissed);
		app.setRecentTotalErrorCount(totalErrorCount);
		app.setRecentTotalFailCount(totalFailCount);
		app.setRecentTotalSkipCount(totalSkipCount);
		app.setRecentTotalRunCount(totalRunCount);
		
		reportRepository.save(report);
		
		appRepository.save(app);
		
		return "success";
	}
	
}
