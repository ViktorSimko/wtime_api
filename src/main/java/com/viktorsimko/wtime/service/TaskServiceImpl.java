package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.model.WorkInterval;
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
  @Autowired
  private ProjectDAO projectDAO;

  @Autowired
  private WorkIntervalService workIntervalService;

  @Override
  public Task getResource(String userName, int resourceId) {
    Task task = super.getResource(userName, resourceId);

    if (task == null) {
      return null;
    }

    Project project = projectDAO.getResource(userName, task.getProjectId());

    int allIncome = workIntervalService.allIncomeForTask(userName, resourceId, project.getHourlyWage());

    task.setAllIncome(allIncome);

    Duration allWorkedTime = workIntervalService.allWorkedTimeForTask(userName, resourceId);

    task.setAllWorkedTime(allWorkedTime);

    return task;
  }

  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    if (projectDAO.getResource(userName, projectId) == null) {
      return null;
    }

    Collection<Task> tasks = ((TaskDAO) resourceDAO).getTasks(userName, projectId);

    tasks.forEach(t -> t.setAllWorkedTime(workIntervalService.allWorkedTimeForTask(userName, t.getId())));

    return tasks;
  }

  @Override
  public Task addResource(Task resource) {
    if (projectDAO.getResource(resource.getUser(), resource.getProjectId()) == null) {
      return null;
    }

    return super.addResource(resource);
  }

  @Override
  public Task updateResource(String userName, int resourceId, Task updatedResource) {
    if (updatedResource.getProjectId() != null && projectDAO.getResource(userName, updatedResource.getProjectId()) == null) {
      return null;
    }

    return super.updateResource(userName, resourceId, updatedResource);
  }

  @Override
  public Task deleteResource(String userName, int projectId) {
    Collection<WorkInterval> workIntervals = workIntervalService.getWorkIntervals(userName, projectId);

    workIntervals.forEach(workInterval -> workIntervalService.deleteResource(userName, workInterval.getId()));

    return super.deleteResource(userName, projectId);
  }

  @Override
  public Duration allWorkedTimeForProject(String userName, int projectId) {

    Collection<Task> tasksForTheProject = getTasks(userName, projectId);

    return tasksForTheProject.stream().reduce(Duration.ZERO, (sum, task) -> sum.plus(workIntervalService.allWorkedTimeForTask(userName, task.getId())), Duration::plus);
  }

  @Override
  public int allIncomeForProject(String userName, int projectId, int hourlyWage) {

    Collection<Task> tasksForTheProject = getTasks(userName, projectId);

    return tasksForTheProject.stream().reduce(0, (sum, task) -> sum + workIntervalService.allIncomeForTask(userName, task.getId(), hourlyWage) + task.getBonus(), (sum1, sum2) -> sum1 + sum2);
  }
}
