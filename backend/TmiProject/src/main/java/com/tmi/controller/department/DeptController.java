package com.tmi.controller.department;

import com.tmi.entity.Department;
import com.tmi.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService service;

    @GetMapping
    ResponseEntity<List<Department>> getAllDept(){
        return new ResponseEntity<>(service.getAllDept(), HttpStatus.OK);
    }

    @GetMapping("/{did}")
    ResponseEntity<Department> getDeptById(@PathVariable int did){
        Department dept = service.getDeptById(did);
        if(dept == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Boolean> postDepartment(@RequestBody Department dept){
        if(dept == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.postDepartment(dept);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{did}")
    ResponseEntity<Boolean> deleteDepartment(@PathVariable int did){
        boolean delete = service.deleteDepartment(did);
        if(delete){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
