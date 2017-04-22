package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * A service for managing and getting information about {@code Task} objects.
 */
@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskDAO taskDAO;

  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    return taskDAO.getTasks(userName, projectId);
  }

  @Override
  public Collection<Task> getTasks(String userName) {
    return taskDAO.getTasks(userName);
  }

  @Override
  public Task getTask(String userName, int taskId) {
    return taskDAO.getTask(userName, taskId);
  }

  @Override
  public Task addTask(Task task) {
    return taskDAO.addTask(task);
  }

  @Override
  public Task updateTask(String userName, int taskId, Task updateInfo) {
    return taskDAO.updateTask(userName, taskId, updateInfo);
  }

  @Override
  public Task deleteTask(String userName, int taskId) {
    return taskDAO.deleteTask(userName, taskId);
  }
}
