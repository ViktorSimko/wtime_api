package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.dao.WorkIntervalDAO;
import com.viktorsimko.wtime.model.WorkInterval;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
@Service
public class WorkIntervalServiceImpl extends ResourceServiceImpl<WorkInterval> implements WorkIntervalService {
  @Autowired
  private TaskDAO taskDAO;

  @Override
  public List<WorkInterval> getWorkIntervals(String userName, int taskId) {
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
    if (updatedResource.getTaskId() != -1 && taskDAO.getResource(userName, updatedResource.getTaskId()) == null) {
      return null;
    }

    return super.updateResource(userName, resourceId, updatedResource);
  }
}
