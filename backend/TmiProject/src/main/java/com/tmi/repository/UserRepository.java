package com.tmi.repository;

import com.tmi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface
UserRepository extends JpaRepository<User, Integer> {
    @Query("select m from User m where uid = :uid and password = :pwd")
    User findUser(String uid, String pwd);

    @Modifying
    @Transactional
    @Query("delete from User where uid = :uid")
    void deleteUser(String uid);
}
