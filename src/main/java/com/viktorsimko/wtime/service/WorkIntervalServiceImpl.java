package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.dao.WorkIntervalDAO;
import com.viktorsimko.wtime.model.WorkInterval;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
@Service
public class WorkIntervalServiceImpl extends ResourceServiceImpl<WorkInterval> implements WorkIntervalService {
  @Autowired
  private TaskDAO taskDAO;

  @Override
  public Collection<WorkInterval> getWorkIntervals(String userName, int taskId) {
    if (taskDAO.getResource(userName, taskId) == null) {
      return null;
    }

    return ((WorkIntervalDAO) resourceDAO).getWorkIntervals(userName, taskId);
  }

  @Override
  public WorkInterval addResource(WorkInterval resource) {
    if (taskDAO.getResource(resource.getUser(), resource.getTaskId()) == null) {
      return null;
    }

    return super.addResource(resource);
  }

  @Override
  public WorkInterval updateResource(String userName, int resourceId, WorkInterval updatedResource) {
    if (updatedResource.getTaskId() != null && taskDAO.getResource(userName, updatedResource.getTaskId()) == null) {
      return null;
    }

    return super.updateResource(userName, resourceId, updatedResource);
  }

  @Override
  public Integer getAllWorkedTime(String userName, int taskId) {
    WorkInterval workInterval = getResource(userName, taskId);

    if (workInterval == null) {
      return null;
    }

    return workInterval.getDuration() == null ? 0 : (int) workInterval.getDuration().getSeconds();
  }

  @Override
  public Integer getAllIncome(String userName, int workIntervalId, int hourlyWage) {
    WorkInterval workInterval = getResource(userName, workIntervalId);

    if (workInterval == null) {
      return null;
    }

    return workInterval.getDuration() == null ? 0 : (int) Math.round(workInterval.getDuration().getSeconds() / 3600.0 * hourlyWage);
  }
}
