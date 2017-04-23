package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.WorkInterval;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by simkoviktor on 2017. 04. 23..
 */
@Repository
public class WorkIntervalDAOImpl extends ResourceDAOImpl<WorkInterval> implements WorkIntervalDAO {
  @Override
  public List<WorkInterval> getWorkIntervals(String userName, int taskId) {
    Session session = sessionFactory.getCurrentSession();

    Query workIntervalsForTask = session.createQuery("from WorkInterval where taskId = :taskId and user = :userName");
    workIntervalsForTask.setParameter("taskId", taskId);
    workIntervalsForTask.setParameter("userName", userName);

    return workIntervalsForTask.getResultList();
  }

  @Override
  public WorkInterval updateResource(String userName, int resourceId, WorkInterval updatedWorkInterval) {
    WorkInterval workIntervalToUpdate = getResource(userName, resourceId);

    if (workIntervalToUpdate == null) {
      return null;
    }

    int newTaskId = updatedWorkInterval.getTaskId();
    LocalDateTime newBegin = updatedWorkInterval.getBegin();
    LocalDateTime newEnd = updatedWorkInterval.getEnd();

    if (newTaskId != -1) {
      workIntervalToUpdate.setTaskId(newTaskId);
    }

    if (newBegin != null) {
      workIntervalToUpdate.setBegin(newBegin);
    }

    if (newEnd != null) {
      workIntervalToUpdate.setEnd(newEnd);
    }

    sessionFactory.getCurrentSession().save(workIntervalToUpdate);

    return workIntervalToUpdate;
  }
}
