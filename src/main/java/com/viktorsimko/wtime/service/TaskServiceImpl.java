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
public class TaskServiceImpl extends ResourceServiceImpl<Task> implements TaskService {
  @Override
  public Collection<Task> getTasks(String userName, int projectId) {
    return ((TaskDAO) resourceDAO).getTasks(userName, projectId);
  }
}
