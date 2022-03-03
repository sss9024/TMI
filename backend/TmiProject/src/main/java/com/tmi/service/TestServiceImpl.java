package com.tmi.service;

import com.tmi.controller.test.TestNotFoundException;
import com.tmi.entity.Report;
import com.tmi.entity.Test;
import com.tmi.repository.ReportRepository;
import com.tmi.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService{
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ReportRepository reportRepository;

    public List<Test> getTestList() {
        return testRepository.findAll();
    }

    public Test getTest(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException(id));
    }

    public List<Test> getTestListByReportId(Long rid) {
        return testRepository.findAllByReport_Id(rid);
    }

    public Test postTest(Test newTest, Long rid) {
        Test NewTest = new Test(newTest);
        Optional<Report> report = reportRepository.findById(rid);
        if (!report.isPresent()) {
            throw new IllegalArgumentException();
        }
        NewTest.setReport(report.get());
        return testRepository.save(NewTest);
    }

    public Test updateTest(Test newTest, Long id) {
        return testRepository.findById(id)
                .map(Test -> {
                    Test.setName(newTest.getName());
                    Test.setType(newTest.getType());
                    Test.setErrorType(newTest.getErrorType());
                    Test.setErrorMessage(newTest.getErrorMessage());
                    Test.setElapsedTime(newTest.getElapsedTime());

                    return testRepository.save(Test);
                })
                .orElseGet(() -> {
                    newTest.setId(id);
                    return testRepository.save(newTest);
                });
    }

    public void deleteTest(Long id) {

        testRepository.deleteById(id);
    }
}
