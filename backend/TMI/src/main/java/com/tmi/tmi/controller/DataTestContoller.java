package com.tmi.tmi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.tmi.tmi.model.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tmi.tmi.model.Test;
import com.tmi.tmi.repository.TestRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class DataTestContoller {
	
	@Autowired
	TestRepository testRepository;
	
	@PostMapping("/junit/data")
	@ApiOperation(value = "postTxtFile")
	public ResponseEntity<String []> postTxtFile(String projectName, String gitUrl, List<MultipartFile> txtFiles, MultipartFile htmlFile) {
		if (!txtFiles.isEmpty() && !htmlFile.isEmpty()) {
			List<String> keyList = new ArrayList<>();
			try {
				Date date_now = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
				String buildTime = sdf.format(date_now);
				keyList.add(buildTime);
				for (MultipartFile txtFile : txtFiles) {
					Test test = new Test();
					test.setBuildTime(buildTime);
					test.setGitUrl(gitUrl);
					test.setProjectName(projectName);
					test.setTestCaseList(new ArrayList<>());
					File file = convert(txtFile);
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					int lineCount = 0;
					while ((line = br.readLine()) != null) {
						String [] splitStr;
						if(lineCount == 1) {
							splitStr = line.split(":");
							String packageName = splitStr[1].trim();
							test.setPackageName(packageName);
							String [] tempSplitStr = packageName.split("[.]");
							test.setPackageShortName(tempSplitStr[tempSplitStr.length-1]);

						}else if(lineCount == 3) {
							splitStr = line.split(",");
							for(int i=0;i<splitStr.length;i++) {
								switch(i) {
								case 0:
									test.setRunCount(Integer.parseInt(splitStr[i].replace("Tests","").split("-")[0].trim().split(":")[1].replace("s","").trim()));
									break;
								case 1:
									test.setFailCount(Integer.parseInt(splitStr[i].replace("Tests","").split("-")[0].trim().split(":")[1].replace("s","").trim()));
									break;
								case 2:
									test.setErrorCount(Integer.parseInt(splitStr[i].replace("Tests","").split("-")[0].trim().split(":")[1].replace("s","").trim()));
									break;
								case 3:
									test.setSkipCount(Integer.parseInt(splitStr[i].replace("Tests","").split("-")[0].trim().split(":")[1].replace("s","").trim()));
									break;
								case 4:
									test.setElapsedTime((int)(Float.parseFloat(splitStr[i].replace("Tests","").split("-")[0].trim().split("<<<")[0].trim().split(":")[1].replace("s","").trim()) * 1000));
									break;
								}

							}
						}else if(lineCount == 4) {
							break;
						}

						lineCount++;
					}
					br.close();
					testRepository.save(test);
				}

				//html 파일 처리
				File file = convert(htmlFile);
				//File file = new File("./surefire-report.html");
				Document doc = Jsoup.parse(file, "UTF-8");
				Elements div = doc.select("div.section");
				int index = 0;
				for(int i=0; i < div.size(); i++){
					if(div.get(i).select("h2").text().equals("Test Cases")){
						index = i;
						break;
					}
				}

				for(int i=1; i < div.get(index).select("div.section").size(); i++){
					//class

					String packageShortName = div.get(index).select("div.section").get(i).select("h3").text();
					List<Test> testList = testRepository.findByPackageShortNameAndBuildTimeAndProjectName(packageShortName, buildTime, projectName);

					Test test = testList.get(0);
					List<TestCase> testCaseList = test.getTestCaseList();

					Elements classes = div.get(index).select("div.section").get(i).select("td");

					for(int j=0; j < classes.size(); j++){
						if(classes.get(j).select("td").hasText()){
							TestCase tc = new TestCase();
							String testCaseName = classes.get(j).select("td").text();
							if(testCaseName.contains("[ Detail ]")) {
								tc.setFail(true);
								testCaseName = testCaseName.replace("+-","").replace("[ Detail ]","").trim();
							}
							else {
								tc.setFail(false);
							}
							if(!isNumber(testCaseName)){
								tc.setTestCaseName(testCaseName);

								while(j < classes.size() - 1){
									j++;
									if(classes.get(j).select("td").hasText())
										break;
								}

								int testTime = (int)(Float.parseFloat(classes.get(j).select("td").text()) * 1000);
								tc.setTestTime(testTime);
							}else {
								int testTime = (int)(Float.parseFloat(testCaseName) * 1000);
								testCaseName = packageShortName;
								tc.setTestCaseName(testCaseName);

								tc.setTestTime(testTime);
							}



							if(tc.isFail()){
								StringBuilder sb = new StringBuilder();

								while(j < classes.size() - 1){
									j++;
									if(classes.get(j).select("td").hasText())
										break;
								}
								sb.append(classes.get(j).select("td").text());
								while (j < classes.size() - 1) {
									j++;
									if (classes.get(j).select("td").hasText())
										break;
								}
								sb.append(classes.get(j).select("td").text());
								tc.setFailDescription(sb.toString());

							}else{
								while(j < classes.size() - 1){
									j++;
									if(classes.get(j).select("td").hasText()){
										if(j < classes.size() && classes.get(j).select("td").text().equals("skipped"))
											tc.setFailDescription("skipped");
										else
											j--;
										break;
									}
								}
							}

							testCaseList.add(tc);
						}
					}

					test.setTestCaseList(testCaseList);
					keyList.add(testRepository.save(test).get_id());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 받아졌다면
			String [] keyArr = keyList.toArray(new String[keyList.size()]);
			return new ResponseEntity<>(keyArr, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static boolean isNumber(String str) {
		boolean check = true;
		for(int i = 0; i < str.length(); i++) {
			// Character클래스의 isDigit() 메소드를 이용하여 문자이면
			// check에 false를 대입하고 break를 이용하여 for문을 빠져나옴
			if(!Character.isDigit(str.charAt(i))) {
				check = false;
				break;
			}
		}
		return check;
	}
}
