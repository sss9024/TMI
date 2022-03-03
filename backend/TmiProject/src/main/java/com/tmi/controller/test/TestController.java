package com.tmi.controller.test;

import com.tmi.entity.Report;
import com.tmi.entity.Test;
import com.tmi.repository.ReportRepository;
import com.tmi.repository.TestRepository;
import com.tmi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService service;

    @GetMapping()
    List<Test> getTestList() {
        return service.getTestList();
    }

    @GetMapping("/{id}")
    Test getTest(@PathVariable Long id) {
        return service.getTest(id);
    }

    @GetMapping("/report/{rid}")
    List<Test> getTestListByReportId(@PathVariable Long rid) {
        return service.getTestListByReportId(rid);
    }

    @PostMapping("/{rid}")
    Test postTest(@RequestBody Test newTest, @PathVariable Long rid) {
        return service.postTest(newTest, rid);
    }

    @PutMapping("/{id}")
    Test updateTest(@RequestBody Test newTest, @PathVariable Long id) {
        return service.updateTest(newTest, id);
    }

    @DeleteMapping("/{id}")
    void deleteTest(@PathVariable Long id) {
        service.deleteTest(id);
    }
}
