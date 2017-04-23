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

@RestController
public class WorkIntervalController {

  @Autowired
  private WorkIntervalService workIntervalService;

  @PostMapping("/work_intervals")
  public ResponseEntity<WorkInterval> addWorkInterval(Authentication authentication, @RequestBody WorkInterval workInterval) {
    String userName = authentication.getName();
    workInterval.setUser(userName);
    WorkInterval savedWorkInterval = workIntervalService.addResource(workInterval);

    return checkResourceCreated(savedWorkInterval);
  }

  @GetMapping("/tasks/{taskId}/work_intervals")
  public ResponseEntity<Collection<WorkInterval>> getWorkIntervals(Authentication authentication, @PathVariable("taskId") int taskId) {
    String userName = authentication.getName();

    Collection<WorkInterval> workIntervals = workIntervalService.getWorkIntervals(userName, taskId);

    return checkResource(workIntervals);
  }

  @GetMapping("/work_intervals")
  public ResponseEntity<Collection<WorkInterval>> getWorkIntervals(Authentication authentication) {
    String userName = authentication.getName();

    Collection<WorkInterval> workIntervals = workIntervalService.getResources(userName);

    return checkResource(workIntervals);
  }

  @GetMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> getWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.getResource(userName, workIntervalId);

    return checkResource(workInterval);
  }

  @PatchMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> patchWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId, @RequestBody WorkInterval updateInfo) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.updateResource(userName, workIntervalId, updateInfo);

    return checkResource(workInterval);
  }

  @DeleteMapping("/work_intervals/{workIntervalId}")
  public ResponseEntity<WorkInterval> deleteWorkInterval(Authentication authentication, @PathVariable("workIntervalId") int workIntervalId) {
    String userName = authentication.getName();

    WorkInterval workInterval = workIntervalService.deleteResource(userName, workIntervalId);

    return checkResource(workInterval);
  }

}
