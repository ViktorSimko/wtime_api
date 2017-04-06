package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 21..
 */
@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskDAO taskDAO;

  @Override
  @Transactional
  public Collection<Task> getTasks(String userName) {
    return null; //taskDAO.getTasks(String userName);
  }

  @Override
  @Transactional
  public void addTask(Task task) {

  }
}