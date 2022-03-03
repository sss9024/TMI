package com.tmi.tmi.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tmi.tmi.model.Counter;
import com.tmi.tmi.model.Coverage;
import com.tmi.tmi.model.Line;
import com.tmi.tmi.model.Method;
import com.tmi.tmi.model.Package;
import com.tmi.tmi.model.PackageInnerClass;
import com.tmi.tmi.model.Sourcefile;
import com.tmi.tmi.repository.CoverageRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class DataXmlController {
	@Autowired
	CoverageRepository coverageRepository;
	
	@PostMapping("/data")
	@ApiOperation(value = "postXmlFile")
	public ResponseEntity<String> postXmlFile(String gitUrl, MultipartFile xmlFile) {
		if (!xmlFile.isEmpty()) {
			try {

				InputStream is = xmlFile.getInputStream();
				InputStreamReader reader = new InputStreamReader(is);
				Stream<String> streamOfString = new BufferedReader(reader).lines();
				String streamToString = streamOfString.collect(Collectors.joining());
				JSONObject xmlJsonObj = XML.toJSONObject(streamToString);

				JSONObject report = xmlJsonObj.getJSONObject("report");
				JSONArray packageArray = report.getJSONArray("package");
				
				Coverage coverage = new Coverage();
				Date date_now = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
				String buildTime = sdf.format(date_now);
				coverage.setBuildTime(buildTime);
				
				List<Package> packageList = new ArrayList<Package>();
				coverage.setProjectName(report.getString("name"));
				JSONArray coverageCounterList = report.getJSONArray("counter");
				
				for(int i=0;i<coverageCounterList.length(); i++) {
					JSONObject coverageCounter = coverageCounterList.getJSONObject(i);
					Counter counter = parseCounter(coverageCounter);
					if (counter.getType().equals("INSTRUCTION")) {
						coverage.setInstruction(counter);
					} else if (counter.getType().equals("LINE")) {
						coverage.setLine(counter);
					} else if (counter.getType().equals("COMPLEXITY")) {
						coverage.setComplexity(counter);
					} else if (counter.getType().equals("METHOD")) {
						coverage.setMethod(counter);
					} else if (counter.getType().equals("CLASS")) {
						coverage.setInnerClass(counter);
					} else {
						coverage.setBranch(counter);
					}
				}
				coverage.setGitUrl(gitUrl);
				for (int i = 0; i < packageArray.length(); i++) {
					Package innerPackage = new Package();
					innerPackage.setName(packageArray.getJSONObject(i).getString("name").replaceAll("/", "."));
					JSONObject packageJson = packageArray.getJSONObject(i);
					if (packageJson.has("counter")) {

						JSONArray packageCounterArray = packageJson.getJSONArray("counter");
						for (int j = 0; j < packageCounterArray.length(); j++) {
							JSONObject packageCounter = packageCounterArray.getJSONObject(j);
							Counter counter = parseCounter(packageCounter);
							if (counter.getType().equals("INSTRUCTION")) {
								innerPackage.setInstruction(counter);
							} else if (counter.getType().equals("LINE")) {
								innerPackage.setLine(counter);
							} else if (counter.getType().equals("COMPLEXITY")) {
								innerPackage.setComplexity(counter);
							} else if (counter.getType().equals("METHOD")) {
								innerPackage.setMethod(counter);
							} else {
								innerPackage.setInnerClass(counter);
							}
						}
					}

					List<Sourcefile> sourceFileList = new ArrayList<Sourcefile>();
					if (packageJson.optJSONArray("sourcefile") == null) {
						Sourcefile sourcefile = new Sourcefile();
						JSONObject sourcefileJson = packageJson.getJSONObject("sourcefile");
						sourcefile.setName(sourcefileJson.getString("name"));
						if (sourcefileJson.has("counter")) {

							JSONArray sourcefileCounterArray = sourcefileJson.getJSONArray("counter");
							for (int j = 0; j < sourcefileCounterArray.length(); j++) {
								JSONObject sourcefileCounter = sourcefileCounterArray.getJSONObject(j);
								Counter counter = parseCounter(sourcefileCounter);
								if (counter.getType().equals("INSTRUCTION")) {
									sourcefile.setInstruction(counter);
								} else if (counter.getType().equals("LINE")) {
									sourcefile.setLine(counter);
								} else if (counter.getType().equals("COMPLEXITY")) {
									sourcefile.setComplexity(counter);
								} else if (counter.getType().equals("METHOD")) {
									sourcefile.setMethod(counter);
								} else if (counter.getType().equals("CLASS")) {
									sourcefile.setInnerClass(counter);
								} else {
									sourcefile.setBranch(counter);
								}
							}
						}
						List<Line> lineList = new ArrayList<Line>();
						if (sourcefileJson.has("line")) {

							if (sourcefileJson.optJSONArray("line") == null) {
								Line line = parseLine(sourcefileJson.getJSONObject("line"));
								lineList.add(line);
							} else {
								JSONArray lineJsonArray = sourcefileJson.getJSONArray("line");
								for (int j = 0; j < lineJsonArray.length(); j++) {
									Line line = parseLine(lineJsonArray.getJSONObject(j));
									lineList.add(line);
								}
							}
						}
						sourcefile.setLineList(lineList);
						sourceFileList.add(sourcefile);
					} else {
						JSONArray sourcefiles = packageJson.getJSONArray("sourcefile");
						for (int j = 0; j < sourcefiles.length(); j++) {
							JSONObject sourcefileJson = sourcefiles.getJSONObject(j);
							Sourcefile sourcefile = new Sourcefile();
							sourcefile.setName(sourcefileJson.getString("name"));

							if (sourcefileJson.has("counter")) {

								JSONArray sourcefileCounterArray = sourcefileJson.getJSONArray("counter");
								for (int k = 0; k < sourcefileCounterArray.length(); k++) {
									JSONObject sourcefileCounter = sourcefileCounterArray.getJSONObject(k);
									Counter counter = parseCounter(sourcefileCounter);
									if (counter.getType().equals("INSTRUCTION")) {
										sourcefile.setInstruction(counter);
									} else if (counter.getType().equals("LINE")) {
										sourcefile.setLine(counter);
									} else if (counter.getType().equals("COMPLEXITY")) {
										sourcefile.setComplexity(counter);
									} else if (counter.getType().equals("METHOD")) {
										sourcefile.setMethod(counter);
									} else if (counter.getType().equals("CLASS")) {
										sourcefile.setInnerClass(counter);
									} else {
										sourcefile.setBranch(counter);
									}
								}
							}

							List<Line> lineList = new ArrayList<Line>();
							if (sourcefileJson.has("line")) {

								if (sourcefileJson.optJSONArray("line") == null) {
									Line line = parseLine(sourcefileJson.getJSONObject("line"));
									lineList.add(line);
								} else {
									
									JSONArray lineJsonArray = sourcefileJson.getJSONArray("line");
									for (int k = 0; k < lineJsonArray.length(); k++) {
										Line line = parseLine(lineJsonArray.getJSONObject(k));
										lineList.add(line);
									}
								}
							}
							sourcefile.setLineList(lineList);
							sourceFileList.add(sourcefile);
						}
					}
					innerPackage.setSourceFileList(sourceFileList);
					//packageList.add(innerPackage);
					List<PackageInnerClass> classList = new ArrayList<PackageInnerClass>();
					if (packageArray.getJSONObject(i).optJSONArray("class") == null) {
						JSONObject pkgInnerClass = packageArray.getJSONObject(i).getJSONObject("class");
						PackageInnerClass pkgClass = new PackageInnerClass();
						pkgClass.setName(pkgInnerClass.get("name").toString().replaceAll("/", "."));
						if (pkgInnerClass.has("counter")) {

							JSONArray classCounterArray = pkgInnerClass.getJSONArray("counter");
							for (int j = 0; j < classCounterArray.length(); j++) {
								JSONObject classCounter = classCounterArray.getJSONObject(j);
								Counter counter = parseCounter(classCounter);
								if (counter.getType().equals("INSTRUCTION")) {
									pkgClass.setInstruction(counter);
								} else if (counter.getType().equals("LINE")) {
									pkgClass.setLine(counter);
								} else if (counter.getType().equals("COMPLEXITY")) {
									pkgClass.setComplexity(counter);
								} else if (counter.getType().equals("METHOD")) {
									pkgClass.setMethod(counter);
								} else {
									pkgClass.setInnerClass(counter);
								}
							}
						}
						if (pkgInnerClass.has("method")) {
							if (pkgInnerClass.optJSONArray("method") == null) {
								List<Method> methodList = new ArrayList<Method>();
								Method method = new Method();
								JSONObject classInnerMethod = pkgInnerClass.getJSONObject("method");
								method.setName(classInnerMethod.get("name").toString());
								JSONArray methodInnerCounter = classInnerMethod.getJSONArray("counter");
								for (int k = 0; k < methodInnerCounter.length(); k++) {
									JSONObject mCounter = methodInnerCounter.getJSONObject(k);
									Counter counter = parseCounter(mCounter);
									if (counter.getType().equals("INSTRUCTION")) {
										method.setInstruction(counter);
									} else if (counter.getType().equals("LINE")) {
										method.setLine(counter);
									} else if (counter.getType().equals("COMPLEXITY")) {
										method.setComplexity(counter);
									} else {
										method.setMethod(counter);
									}
								}
								methodList.add(method);
								pkgClass.setMethodList(methodList);
							} else {
								JSONArray classInnerMethods = pkgInnerClass.getJSONArray("method");
								List<Method> methodList = new ArrayList<Method>();
								for (int j = 0; j < classInnerMethods.length(); j++) {
									Method method = new Method();
									method.setName(classInnerMethods.getJSONObject(j).get("name").toString());
									JSONArray methodInnerCounter = classInnerMethods.getJSONObject(j)
											.getJSONArray("counter");
									for (int k = 0; k < methodInnerCounter.length(); k++) {
										JSONObject mCounter = methodInnerCounter.getJSONObject(k);
										Counter counter = parseCounter(mCounter);
										if (counter.getType().equals("INSTRUCTION")) {
											method.setInstruction(counter);
										} else if (counter.getType().equals("LINE")) {
											method.setLine(counter);
										} else if (counter.getType().equals("COMPLEXITY")) {
											method.setComplexity(counter);
										} else {
											method.setMethod(counter);
										}
									}
									methodList.add(method);
								}
								pkgClass.setMethodList(methodList);
							}
						}
						classList.add(pkgClass);
					} else {
						JSONArray pkgInnerClasses = packageArray.getJSONObject(i).getJSONArray("class");
						for (int j = 0; j < pkgInnerClasses.length(); j++) {
							JSONObject pkgInnerClass = pkgInnerClasses.getJSONObject(j);
							PackageInnerClass pkgClass = new PackageInnerClass();
							pkgClass.setName(pkgInnerClass.get("name").toString().replaceAll("/", "."));
							if (pkgInnerClass.has("counter")) {

								JSONArray classCounterArray = pkgInnerClass.getJSONArray("counter");
								for (int k = 0; k < classCounterArray.length(); k++) {
									JSONObject classCounter = classCounterArray.getJSONObject(k);
									Counter counter = parseCounter(classCounter);
									if (counter.getType().equals("INSTRUCTION")) {
										pkgClass.setInstruction(counter);
									} else if (counter.getType().equals("LINE")) {
										pkgClass.setLine(counter);
									} else if (counter.getType().equals("COMPLEXITY")) {
										pkgClass.setComplexity(counter);
									} else if (counter.getType().equals("METHOD")) {
										pkgClass.setMethod(counter);
									} else {
										pkgClass.setInnerClass(counter);
									}
								}
							}
							if (pkgInnerClass.has("method")) {

								if (pkgInnerClass.optJSONArray("method") == null) {
									JSONObject classInnerMethod = pkgInnerClass.getJSONObject("method");
									List<Method> methodList = new ArrayList<Method>();
									Method method = new Method();
									method.setName(classInnerMethod.get("name").toString());
									JSONArray methodInnerCounter = classInnerMethod.getJSONArray("counter");
									for (int l = 0; l < methodInnerCounter.length(); l++) {
										JSONObject mCounter = methodInnerCounter.getJSONObject(l);
										Counter counter = parseCounter(mCounter);
										if (counter.getType().equals("INSTRUCTION")) {
											method.setInstruction(counter);
										} else if (counter.getType().equals("LINE")) {
											method.setLine(counter);
										} else if (counter.getType().equals("COMPLEXITY")) {
											method.setComplexity(counter);
										} else {
											method.setMethod(counter);
										}
									}
									methodList.add(method);
									pkgClass.setMethodList(methodList);
								} else {
									JSONArray classInnerMethods = pkgInnerClass.getJSONArray("method");
									List<Method> methodList = new ArrayList<Method>();
									for (int k = 0; k < classInnerMethods.length(); k++) {
										Method method = new Method();
										method.setName(classInnerMethods.getJSONObject(k).get("name").toString());
										JSONArray methodInnerCounter = classInnerMethods.getJSONObject(k)
												.getJSONArray("counter");
										for (int l = 0; l < methodInnerCounter.length(); l++) {
											JSONObject mCounter = methodInnerCounter.getJSONObject(l);
											Counter counter = parseCounter(mCounter);
											if (counter.getType().equals("INSTRUCTION")) {
												method.setInstruction(counter);
											} else if (counter.getType().equals("LINE")) {
												method.setLine(counter);
											} else if (counter.getType().equals("COMPLEXITY")) {
												method.setComplexity(counter);
											} else {
												method.setMethod(counter);
											}
										}
										methodList.add(method);
									}
									pkgClass.setMethodList(methodList);
								}
							}
							classList.add(pkgClass);
						}
					}
					innerPackage.setClassList(classList);
					packageList.add(innerPackage);
				}
				//System.out.println(packageList);
				
				coverage.setPackageList(packageList);
				//System.out.println("id " + coverageRepository.save(coverage).get_id());
				
				return new ResponseEntity<>(report.getString("name") + "  " + coverageRepository.save(coverage).get_id(), HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

	}

	public Counter parseCounter(JSONObject obj) {
		Counter counter = new Counter();
		counter.setType(obj.get("type").toString());
		counter.setCovered(Integer.parseInt(obj.get("covered").toString()));
		counter.setMissed(Integer.parseInt(obj.get("missed").toString()));
		return counter;
	}

	public Line parseLine(JSONObject obj) {
		Line line = new Line();
		line.setNr(obj.getInt("nr"));
		line.setMi(obj.getInt("mi"));
		line.setCi(obj.getInt("ci"));
		line.setMb(obj.getInt("mb"));
		line.setCb(obj.getInt("cb"));

		return line;
	}
}