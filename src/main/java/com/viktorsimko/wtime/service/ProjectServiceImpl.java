package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

/**
 * A service for managing project resources.
 */
@Service
public class ProjectServiceImpl extends ResourceServiceImpl<Project> implements ProjectService {
  private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Override
  public Integer getAllIncome(String userName, int projectId) {
    Project project = getResource(userName, projectId);

    if (project == null) {
      logger.info("requested project: {} for user: {} not found", projectId, userName);

      return null;
    }

    return taskService.getTasks(userName, projectId).stream()
      .mapToInt(task -> taskService.getAllIncome(userName, task.getId(), project.getHourlyWage()))
      .sum();
  }

  @Override
  public Integer getAllWorkedTime(String userName, int projectId) {
    Project project = getResource(userName, projectId);

    if (project == null) {
      logger.info("requested project: {} for user: {} not found", projectId, userName);

      return null;
    }

    return taskService.getTasks(userName, projectId).stream()
      .mapToInt(task -> taskService.getAllWorkedTime(userName, task.getId()))
      .sum();
  }

  @Override
  public Project deleteResource(String userName, int projectId) {

    Collection<Task> tasksToRemove = taskService.getTasks(userName, projectId);

    if (tasksToRemove != null) {
      tasksToRemove.forEach(task -> taskService.deleteResource(userName, task.getId()));
    }

    return super.deleteResource(userName, projectId);
  }
}
