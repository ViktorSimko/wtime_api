package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Class for accessing and manipulating the tasks in the database.
 */
@Repository
public class TaskDAOImpl extends ResourceDAOImpl<Task> implements TaskDAO {

  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    Session session = sessionFactory.getCurrentSession();

    Query tasksForProjectQuery = session.createQuery("from Task where user = :userName and projectId = :projectId");
    tasksForProjectQuery.setParameter("userName", userName);
    tasksForProjectQuery.setParameter("projectId", projectId);

    List<Task> tasks = tasksForProjectQuery.getResultList();

    return tasks;
  }

  @Override
  public Task updateResource(String userName, int taskId, Task updateInfo) {

    Task task = getResource(userName, taskId);

    task.setTitle(updateInfo.getTitle());

    task.setProjectId(updateInfo.getProjectId());

    sessionFactory.getCurrentSession().save(task);

    return task;
  }
}
