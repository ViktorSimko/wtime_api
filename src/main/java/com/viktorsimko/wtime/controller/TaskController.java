package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private Logger logger = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private TaskService taskService;

  /**
   * Saves a task and returns it in a response.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param task the task to add
   * @return the added task in a response
   */
  @PostMapping("/tasks")
  public ResponseEntity<Task> addTask(Authentication authentication, @RequestBody Task task) {
    String userName = authentication.getName();
    task.setUser(userName);
    Task savedTask = taskService.addResource(task);

    logger.debug("POST /tasks user: {}", userName);

    return checkResourceCreated(savedTask);
  }

  /**
   * Returns the tasks for the given project in a response.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project to query the tasks for
   * @return a response containing the tasks, or null if a project not found with the given id
   */
  @GetMapping("/projects/{projectId}/tasks")
  public ResponseEntity<Collection<Task>> getTasks(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();

    Collection<Task> tasks = taskService.getTasks(userName, projectId);

    logger.debug("GET /projects/{}/tasks user: {}", projectId, userName);

    return checkResource(tasks);
  }

  /**
   * Returns the tasks of the logged in user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @return a response including the tasks of the user
   */
  @GetMapping("/tasks")
  public ResponseEntity<Collection<Task>> getTasks(Authentication authentication) {
    String userName = authentication.getName();

    Collection<Task> tasks = taskService.getResources(userName);

    logger.debug("GET /tasks user: {}", userName);

    return checkResource(tasks);
  }

  /**
   * Returns the task with the given id.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task
   * @return a response containing the task, or null if a task not found with the given id
   */
  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<Task> getTask(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Task task = taskService.getResource(userName, taskId);

    logger.debug("GET /tasks/{} user: {}", taskId, userName);

    return checkResource(task);
  }

  /**
   * Returns the value of all the income from the given task.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task
   * @return all the income for the task, or null if a task not found with the given id
   */
  @GetMapping("/tasks/{taskId}/allIncome")
  public ResponseEntity<Integer> getAllIncomeForProject(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();
    Integer allIncome = taskService.getAllIncome(userName, taskId);

    logger.debug("GET /tasks/{}/allIncome user: {}", taskId, userName);

    return checkResource(allIncome);
  }

  /**
   * Returns the quantity of time worked on the task in seconds.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task
   * @return the time spent with the task in seconds, or null if a task not found with the given id
   */
  @GetMapping("/tasks/{taskId}/allWorkedTime")
  public ResponseEntity<Integer> getAllWorkedTimeForProject(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();
    Integer allWorkedTime = taskService.getAllWorkedTime(userName, taskId);

    logger.debug("GET /tasks/{}/allWorkedTime user: {}", taskId, userName);

    return checkResource(allWorkedTime);
  }

  /**
   * Updates the task with the given id with the values from {@code updateInfo}.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task
   * @param updateInfo the updated task
   * @return a response containing the updated task, or null if a task not found with the given id
   */
  @PatchMapping("/tasks/{taskId}")
  public ResponseEntity<Task> patchTask(Authentication authentication, @PathVariable("taskId") int taskId, @RequestBody Task updateInfo) {
    String userName = authentication.getName();

    Task task = taskService.updateResource(userName, taskId, updateInfo);

    logger.debug("PATCH /tasks/{} user: {}", taskId, userName);

    return checkResource(task);
  }

  /**
   * Deletes the task with the given id.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task
   * @return the deleted task, or null if a task not found with the given id
   */
  @DeleteMapping("/tasks/{taskId}")
  public ResponseEntity<Task> deleteTask(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Task task = taskService.deleteResource(userName, taskId);

    logger.debug("DELETE /tasks/{} user: {}", taskId, userName);

    return checkResource(task);
  }

}
