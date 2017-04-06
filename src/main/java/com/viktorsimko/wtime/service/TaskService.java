package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Task;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 21..
 */
public interface TaskService {

  Collection<Task> getTasks(String userName);

  void addTask(Task task);

}
