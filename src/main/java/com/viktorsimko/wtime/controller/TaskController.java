package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 19..
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

    Project project = projectService.getProject(userName, projectId);
    project.getTasks().add(task);
  }

  @GetMapping
  public Collection<Task> getTasks(Authentication authentication,
                                   @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getProject(userName, projectId);
    return project.getTasks();
  }

}
