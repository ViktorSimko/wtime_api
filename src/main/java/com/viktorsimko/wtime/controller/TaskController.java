package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 *
 */
@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

  @Autowired
  ProjectService projectService;

  @PostMapping
  public void addTask(Authentication authentication,
                      @PathVariable("projectId") int projectId,
                      @RequestBody Task task) {
    String userName = authentication.getName();

  }

  @GetMapping
  public Collection<Task> getTasks(Authentication authentication,
                                   @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();

    return null;
  }

}
