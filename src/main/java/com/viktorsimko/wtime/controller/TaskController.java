package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;
import static com.viktorsimko.wtime.util.ResourceChecker.checkResourceCreated;

/**
 * A controller for working with {@code Task} resources.
 */
@RestController
public class TaskController {

  @Autowired
  private TaskService taskService;

  @PostMapping("/tasks")
  public ResponseEntity<Task> addTask(Authentication authentication, @RequestBody Task task) {
    String userName = authentication.getName();
    task.setUser(userName);
    Task savedTask = taskService.addResource(task);

    return checkResourceCreated(savedTask);
  }

  @GetMapping("/projects/{projectId}/tasks")
  public ResponseEntity<Collection<Task>> getTasks(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();

    Collection<Task> tasks = taskService.getTasks(userName, projectId);

    return checkResource(tasks);
  }

  @GetMapping("/tasks")
  public ResponseEntity<Collection<Task>> getTasks(Authentication authentication) {
    String userName = authentication.getName();

    Collection<Task> tasks = taskService.getResources(userName);

    return checkResource(tasks);
  }

  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<Task> getTask(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Task task = taskService.getResource(userName, taskId);

    return checkResource(task);
  }

  @PatchMapping("/tasks/{taskId}")
  public ResponseEntity<Task> patchTask(Authentication authentication, @PathVariable("taskId") int taskId, @RequestBody Task updateInfo) {
    String userName = authentication.getName();

    Task task = taskService.updateResource(userName, taskId, updateInfo);

    return checkResource(task);
  }

  @DeleteMapping("/tasks/{taskId}")
  public ResponseEntity<Task> deleteTask(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Task task = taskService.deleteResource(userName, taskId);

    return checkResource(task);
  }

}
