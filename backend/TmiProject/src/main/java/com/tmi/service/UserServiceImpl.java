package com.tmi.service;

import com.tmi.entity.Department;
import com.tmi.entity.User;
import com.tmi.repository.DeptRepository;
import com.tmi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repo;

    @Autowired
    private DeptRepository deptRepo;

    @Override
    public List<User> getAllUser() {
        return repo.findAll();
    }

    @Override
    public User login(String uid, String pwd) {
        return repo.findUser(uid, pwd);
    }

    @Override
    public void deleteUser(String uid) {
        repo.deleteUser(uid);
    }

    @Override
    public User postUser(User user, int id) {
        User newUser = user;
        if(newUser.getRole() == null){
            newUser.setRole("user");
        }
        deptRepo.findById(id).ifPresent(selected -> {
            newUser.setDepartment(selected);
        });
        return repo.save(newUser);
    }

    @Override
    public void putUser(User user, int did) {
        repo.findById(user.getId()).ifPresent(selected -> {
            if(user.getRole() == null){
                user.setRole("user");
            }
            selected.setRole(user.getRole());
            deptRepo.findById(did).ifPresent(selectedDept -> {
                selected.setDepartment(selectedDept);
            });
            selected.setName(user.getName());
            selected.setPassword(user.getPassword());
            repo.save(selected);
        });
    }
}
