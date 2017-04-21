package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 21..
 */
public interface TaskDAO {

  Collection<Task> getTasks(String userName, int projectId);

  Task getTask(String userName, int taskId);

  void addTask(Task task);

}
