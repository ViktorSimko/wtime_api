package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.WorkInterval;
import com.viktorsimko.wtime.service.WorkIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;
import static com.viktorsimko.wtime.util.ResourceChecker.checkResourceCreated;

/**
 * A controller for handling {@code WorkInterval} objects.
 */
@RestController
public class WorkIntervalController {

  @Autowired
  private WorkIntervalService workIntervalService;

  /**
   * Saves the given work interval.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param workInterval the work interval to save
   * @return a response with the saved worked interval
   */
  @PostMapping("/work_intervals")
  public ResponseEntity<WorkInterval> addWorkInterval(Authentication authentication, @RequestBody WorkInterval workInterval) {
    String userName = authentication.getName();
    workInterval.setUser(userName);
    WorkInterval savedWorkInterval = workIntervalService.addResource(workInterval);

    return checkResourceCreated(savedWorkInterval);
  }

  /**
   * Returns the work intervals of a given task.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param taskId the id of the task to query the work intervals for
   * @return a response containing the work intervals, or null if the {@code Task} not exists
   */
  @GetMapping("/tasks/{taskId}/work_intervals")
  public ResponseEntity<Collection<WorkInterval>> getWorkIntervals(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Collection<WorkInterval> workIntervals = workIntervalService.getWorkIntervals(userName, taskId);

    return checkResource(workIntervals);
  }

  /**
   * Returns the work intervals for the current user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @return a response with the work intervals for the user
   */
  @GetMapping("/work_intervals")
  public ResponseEntity<Collection<WorkInterval>> getWorkIntervals(Authentication authentication) {
    String userName = authentication.getName();

    Collection<WorkInterval> workIntervals = workIntervalService.getResources(userName);

    return checkResource(workIntervals);
  }

  /**
   * Returns the {@code WorkInterval} for the given id.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param workIntervalId the id of the work interval
   * @return a response with the work interval, or null if a work interval with the given id not exists
   */
  @GetMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> getWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.getResource(userName, workIntervalId);

    return checkResource(workInterval);
  }

  /**
   * Updates the work interval with the given id using the update data.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param workIntervalId the id of the work interval
   * @param updateInfo the updated {@code WorkInterval}
   * @return the updated {@code WorkInterval} in a response, or null if a work interval with the given id not exists
   */
  @PatchMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> patchWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId, @RequestBody WorkInterval updateInfo) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.updateResource(userName, workIntervalId, updateInfo);

    return checkResource(workInterval);
  }

  /**
   * Deletes the given {@code WorkInterval}.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param workIntervalId the id of the work interval to delete
   * @return a response containing the deleted work interval, or null if a work interval with the given id not exists
   */
  @DeleteMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> deleteWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.deleteResource(userName, workIntervalId);

    return checkResource(workInterval);
  }

}
