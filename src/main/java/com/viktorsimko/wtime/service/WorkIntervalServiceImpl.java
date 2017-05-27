package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.dao.WorkIntervalDAO;
import com.viktorsimko.wtime.model.WorkInterval;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * {@code WorkInterval} specific extension of {@code ResourceServiceImpl}.
 */
@Service
public class WorkIntervalServiceImpl extends ResourceServiceImpl<WorkInterval> implements WorkIntervalService {
  private Logger logger = LoggerFactory.getLogger(WorkIntervalServiceImpl.class);

  @Autowired
  private TaskDAO taskDAO;

  @Override
  public Collection<WorkInterval> getWorkIntervals(String userName, int taskId) {
    if (taskDAO.getResource(userName, taskId) == null) {
      logger.info("requested task: {} for user: {} not found", userName, taskId);
      return null;
    }

    return ((WorkIntervalDAO) resourceDAO).getWorkIntervals(userName, taskId);
  }

  @Override
  public WorkInterval addResource(WorkInterval resource) {
    if (taskDAO.getResource(resource.getUser(), resource.getTaskId()) == null) {
      logger.info("requested task: {} for user: {} not found", userName, taskId);
      return null;
    }

    return super.addResource(resource);
  }

  @Override
  public WorkInterval updateResource(String userName, int resourceId, WorkInterval updatedResource) {
    if (updatedResource.getTaskId() != null && taskDAO.getResource(userName, updatedResource.getTaskId()) == null) {
      logger.info("requested task: {} for user: {} not found", userName, updatedResource.getTaskId());
      return null;
    }

    return super.updateResource(userName, resourceId, updatedResource);
  }

  @Override
  public Integer getAllWorkedTime(String userName, int workIntervalId) {
    WorkInterval workInterval = getResource(userName, workIntervalId);

    if (workInterval == null) {
      logger.info("requested work interval: {} for user: {} not found", userName, workIntervalId);
      return null;
    }

    return (int) workInterval.getDuration().getSeconds();
  }

  @Override
  public Integer getAllIncome(String userName, int workIntervalId, int hourlyWage) {
    WorkInterval workInterval = getResource(userName, workIntervalId);

    if (workInterval == null) {
      logger.info("requested work interval: {} for user: {} not found", userName, workIntervalId);
      return null;
    }

    return (int) Math.round(workInterval.getDuration().getSeconds() / 3600.0 * hourlyWage);
  }
}
