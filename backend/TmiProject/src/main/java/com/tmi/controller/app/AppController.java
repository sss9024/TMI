package com.tmi.controller.app;

import java.util.List;
import com.tmi.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmi.entity.App;
import com.tmi.repository.AppRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/app")
public class AppController {
	@Autowired
	private AppService service;
    @Autowired
    private AppRepository repo;

	@GetMapping
	ResponseEntity<List<App>> getAllApp() {
		List<App> list = service.getAllApp();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	ResponseEntity<App> getApp(@PathVariable String id) {
		App app = service.findById(id);
		if(app == null){
			throw new AppNotFoundException(id);
		}
		return new ResponseEntity<>(app, HttpStatus.OK);
	}

	@GetMapping("/project/{pid}")
	ResponseEntity<List<App>> getAppListByProjectId(@PathVariable Long pid) {
		List<App> app = service.getAppListByProjectId(pid);
		return new ResponseEntity<>(app, HttpStatus.OK);
	}

    @PostMapping("/project/{id}")
    App postAppAtProject(@RequestBody App app, @PathVariable long id) {
        return service.postAppAtProject(app, id);
    }

	@DeleteMapping("/{id}")
	boolean deleteAppById(@PathVariable String id) {
		service.deleteAppById(id);
		return false;
	}

	@PutMapping
	ResponseEntity<Boolean> putAppData(@RequestBody App app) {
		if(app == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{
			service.putAppData(app);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
