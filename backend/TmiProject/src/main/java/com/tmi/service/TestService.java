package com.tmi.service;

import com.tmi.entity.Test;

import java.util.List;

public interface TestService {
    List<Test> getTestList();
    Test getTest(Long id);
    List<Test> getTestListByReportId(Long rid);
    Test postTest(Test newTest, Long rid);
    Test updateTest(Test newTest, Long id);
    void deleteTest(Long id);
}
