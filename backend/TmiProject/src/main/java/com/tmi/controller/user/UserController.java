package com.tmi.controller.user;

import com.tmi.entity.Department;
import com.tmi.entity.User;
import com.tmi.repository.DeptRepository;
import com.tmi.repository.UserRepository;
import com.tmi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAllUser(){
        return service.getAllUser();
    }

    @PostMapping("/{dept_id}")
    public ResponseEntity<User> postUser(@RequestBody User user, @PathVariable int dept_id){
        if(user == null || dept_id == 0){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.postUser(user, dept_id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> map){
        String uid = map.get("id");
        String pwd = map.get("pwd");

        if(uid == null || pwd == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(service.login(uid, pwd), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public void deleteUser(@PathVariable String uid){
        service.deleteUser(uid);
    }

    @PutMapping("/{did}")
    public void putUser(@RequestBody User user, @PathVariable int did){
        service.putUser(user, did);
    }

}
