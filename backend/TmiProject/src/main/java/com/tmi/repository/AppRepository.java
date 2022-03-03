package com.tmi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tmi.entity.App;

public interface AppRepository extends JpaRepository<App, String> {
    List<App> findAllByProjectIdEquals(Long pid);
    
    @Query("select m from App m where title = :title and git_url = :gitUrl")
    App findApp(String title, String gitUrl);
}
