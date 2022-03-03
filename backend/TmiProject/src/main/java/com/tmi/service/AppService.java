package com.tmi.service;

import com.tmi.entity.App;

import java.util.List;

public interface AppService {
    List<App> getAllApp();
    List<App> getAppListByProjectId(Long pid);
    boolean deleteAppById(String id);
    void putAppData(App app);
    App findById(String id);
    App postAppAtProject(App app, long id);
}
