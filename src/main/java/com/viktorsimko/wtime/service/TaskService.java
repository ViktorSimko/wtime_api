package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Task;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * A service for managing and getting information about {@code Task} objects.
 */
public interface TaskService {

  /**
   * Returns the {@code Task}'s associated with the given {@code Project}.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the {@code id} of the {@code Project} to get the tasks for
   * @return the {@code Task}'s associated with the given {@code Project}
   *         or null if the given {@code id} is not associated with the {@code userName} or it is not exists
   */
  Collection<Task> getTasks(String userName, int projectId);

  /**
   * Returns the {@code Task}'s associated with the given user.
   *
   * @param userName the user to get the {@code Task}'s for
   * @return the {@code Task}'s associated with the given user
   */
  Collection<Task> getTasks(String userName);

  /**
   * Returns the {@code Task}'s associated with the given {@code id}.
   *
   * @param userName the {@code name} of the current user
   * @param taskId the {@code id} of the task to get
   * @return the {@code Task} associated with the given {@code id},
   *         or null if the given {@code id} is not associated with the {@code userName} or it is not exists
   */
  Task getTask(String userName, int taskId);

  /**
   * Adds the given {@code Task} object for the given {@code Project} to the database.
   *
   * @param task the {@code Task} to save
   */
  void addTask(Task task);
}
