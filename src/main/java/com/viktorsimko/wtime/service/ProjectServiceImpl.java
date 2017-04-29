package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * A service for managing project resources.
 */
@Service
public class ProjectServiceImpl extends ResourceServiceImpl<Project> implements ProjectService {
  @Autowired
  private TaskService taskService;

  @Override
  public Project getResource(String userName, int projectId) {
    Project project = super.getResource(userName, projectId);

    if (project == null) {
      return null;
    }

//    int allIncome = taskService.allIncomeForProject(projectId);
//
//    project.setAllIncome(allIncome);
    Duration allWorkedTime = taskService.allWorkedTimeForProject(userName, projectId);
    project.setAllWorkedTime(allWorkedTime);

    return project;
  }

  @Override
  public Collection<Project> getResources(String userName) {
    Collection<Project> projects = super.getResources(userName);

    projects.forEach(p -> p.setAllWorkedTime(taskService.allWorkedTimeForProject(userName, p.getId())));

    return projects;
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
