package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Class for accessing and manipulating the tasks in the database.
 */
@Repository
public class TaskDAOImpl implements TaskDAO {

  @Autowired
  SessionFactory sessionFactory;

  @Override
  @Transactional
  public Collection<Task> getTasks(String userName, int projectId) {
    Session session = sessionFactory.getCurrentSession();

    Query tasksForProjectQuery = session.createQuery("from Task where user = :userName and projectId = :projectId");
    tasksForProjectQuery.setParameter("userName", userName);
    tasksForProjectQuery.setParameter("projectId", projectId);

    List<Task> tasks = tasksForProjectQuery.getResultList();

    return tasks;
  }

  @Override
  @Transactional
  public Collection<Task> getTasks(String userName) {
    Session session = sessionFactory.getCurrentSession();

    Query tasksQuery = session.createQuery("from Task where user = :userName");
    tasksQuery.setParameter("userName", userName);

    List<Task> tasks = tasksQuery.getResultList();

    return tasks;
  }

  @Override
  @Transactional
  public Task getTask(String userName, int taskId) {
    Session session = sessionFactory.getCurrentSession();

    Query taskQuery = session.createQuery("from Task where id = :taskId user = :userName");
    taskQuery.setParameter("id", taskId);
    taskQuery.setParameter("userName", userName);

    List<Task> tasks = taskQuery.getResultList();

    if (tasks.size() == 0) {
      return null;
    }

    return tasks.get(0);
  }

  @Override
  @Transactional
  public Task addTask(Task task) {

    sessionFactory.getCurrentSession().save(task);

    return task;
  }

  @Override
  @Transactional
  public Task updateTask(String userName, int taskId, Task updateInfo) {

    Task task = getTask(userName, taskId);

    task.setTitle(updateInfo.getTitle());

    task.setProjectId(updateInfo.getProjectId());

    sessionFactory.getCurrentSession().save(task);

    return task;
  }

  @Override
  public Task deleteTask(String userName, int taskId) {

    Task task = getTask(userName, taskId);

    sessionFactory.getCurrentSession().delete(task);

    return task;
  }
}
