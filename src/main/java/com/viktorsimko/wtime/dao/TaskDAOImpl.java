package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 21..
 */
@Repository
public class TaskDAOImpl implements TaskDAO {
  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    return null;
  }

  @Override
  public Task getTask(String userName, int taskId) {
    return null;
  }

  @Override
  public void addTask(Task task) {

  }
}
