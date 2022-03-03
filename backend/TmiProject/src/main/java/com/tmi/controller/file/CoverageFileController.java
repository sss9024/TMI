package com.tmi.controller.file;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

import com.tmi.entity.Coverage;
import com.tmi.entity.Report;
import com.tmi.repository.AppRepository;
import com.tmi.repository.ReportRepository;
import com.tmi.service.CoverageService;
import com.tmi.service.ReportService;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class CoverageFileController {
	@Value("${spring.file.location}")
	private String storagePath;
	@Autowired
	AppRepository appRepository;
	@Autowired
	ReportRepository reportRepository;
	@Autowired
	CoverageService coverageService;
	@Autowired
	ReportService reportService;

	
	@PostMapping
	ResponseEntity<String> coverageFileSave(String projectName, String gitUrl, String buildTime, MultipartFile zipFile) {
		
		String appId = appRepository.findApp(projectName, gitUrl).getId();
		
		String filePath = storagePath + "/" + appId + "/" + buildTime.replace(":", "").replace(" ", "_") + "/";
		
		String url = "https://k4a201.p.ssafy.io/static/coverage/" + appId + "/" + buildTime.replace(":", "").replace(" ", "_") + "/";
		
		zipFile.getOriginalFilename();
		File file = new File(filePath + zipFile.getOriginalFilename());
		
		if(file.getParentFile().mkdirs()) {
			try {
				// 물리파일 생성
				file.createNewFile();
			}
			catch(IOException ie) {
				// 파일 생성 중 오류
				ie.printStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}			
		}
			
		try {
			// 업로드 임시파일 -> 물리파일 덮어쓰기
			zipFile.transferTo(file);
		}
		catch(IOException ie) {
			// 덮어쓰기 중 오류
			ie.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		ZipUtil.unpack(file, new File(filePath));
		
		file.delete();
		
		List<Report> reportList = reportService.readAllReportsInApp(appId);
		
		Long reportId = 0L;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for(Report report : reportList) {
			String datetime = report.getDatetime().format(formatter);
			if(datetime.equals(buildTime)) {
				reportId = report.getId();
				break;
			}
		}
		
		List<Coverage> coverageList = coverageService.readAllCoveragesInReport(reportId);
		
		for(Coverage coverage : coverageList) {
			File htmlFile = new File(filePath + "/" + coverage.getPackageName() + "/" + coverage.getClassName() + ".html");
			
			if(htmlFile.exists()) {
				//coverage.setHighlightHtml(htmlFile.getAbsolutePath());
				coverage.setHighlightHtml(url + "/" + coverage.getPackageName() + "/" + coverage.getClassName() + ".html");
			}
			
			coverageService.saveCoverage(coverage);
		}
		
		return new ResponseEntity<>(file.getAbsolutePath(),HttpStatus.OK);
	}
}
