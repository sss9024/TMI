package com.tmi.service;

import com.tmi.entity.Department;
import com.tmi.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUser();

    User login(String uid, String pwd);

    void deleteUser(String uid);

    User postUser(User user, int id);

    void putUser(User user, int did);
}
