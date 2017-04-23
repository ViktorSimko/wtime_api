package com.viktorsimko.wtime.service;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.model.WorkInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
  public Collection<Task> getTasks(String userName, int projectId) {
    if (projectDAO.getResource(userName, projectId) == null) {
      return null;
    }

    return ((TaskDAO) resourceDAO).getTasks(userName, projectId);
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
    if (updatedResource.getProjectId() != -1 && projectDAO.getResource(userName, updatedResource.getProjectId()) == null) {
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
}
