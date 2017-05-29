package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.model.WorkInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service for managing and getting information about {@code Task} objects.
 */
@Service
public class TaskServiceImpl extends ResourceServiceImpl<Task> implements TaskService {

  private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

  @Autowired
  private ProjectDAO projectDAO;

  @Autowired
  private WorkIntervalService workIntervalService;

  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    if (projectDAO.getResource(userName, projectId) == null) {
      logger.info("requested project: {} for user: {} not found", projectId, userName);
      return null;
    }

    return ((TaskDAO) resourceDAO).getTasks(userName, projectId);
  }

  @Override
  public Task addResource(Task resource) {
    if (projectDAO.getResource(resource.getUser(), resource.getProjectId()) == null) {
      logger.info("requested project: {} for user: {} not found", resource.getProjectId(), resource.getUser());
      return null;
    }

    return super.addResource(resource);
  }

  @Override
  public Task updateResource(String userName, int resourceId, Task updatedResource) {
    if (updatedResource.getProjectId() != null && projectDAO.getResource(userName, updatedResource.getProjectId()) == null) {
      logger.info("requested project: {} for user: {} not found", updatedResource.getProjectId(), userName);
      return null;
    }

    return super.updateResource(userName, resourceId, updatedResource);
  }

  @Override
  public Task deleteResource(String userName, int taskId) {
    Collection<WorkInterval> workIntervals = workIntervalService.getWorkIntervals(userName, taskId);

    workIntervals.forEach(workInterval -> workIntervalService.deleteResource(userName, workInterval.getId()));

    return super.deleteResource(userName, taskId);
  }

  @Override
  public Integer getAllWorkedTime(String userName, int taskId) {

    Task task = getResource(userName, taskId);

    if (task == null) {
      logger.info("requested task: {} for user: {} not found", taskId, userName);
      return null;
    }

    return workIntervalService.getWorkIntervals(userName, taskId).stream()
      .mapToInt(workInterval -> workIntervalService.getAllWorkedTime(userName, workInterval.getId()))
      .sum();
  }

  @Override
  public Integer getAllIncome(String userName, int taskId, int hourlyWage) {
    Task task = getResource(userName, taskId);

    if (task == null) {
      logger.info("requested task: {} for user: {} not found", taskId, userName);
      return null;
    }

    return workIntervalService.getWorkIntervals(userName, taskId).stream()
      .mapToInt((workInterval) -> workIntervalService.getAllIncome(userName, workInterval.getId(), hourlyWage)).sum() + task.getBonus();
  }

  @Override
  public Integer getAllIncome(String userName, int taskId) {
    Task task = getResource(userName, taskId);

    if (task == null) {
      logger.info("requested task: {} for user: {} not found", taskId, userName);
      return null;
    }

    Project project = projectDAO.getResource(userName, task.getProjectId());

    if (project == null) {
      logger.info("requested project: {} for user: {} not found", task.getProjectId(), userName);
      return null;
    }

    return getAllIncome(userName, taskId, project.getHourlyWage());
  }
}
