package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Task;

import java.time.Duration;
import java.util.Collection;

/**
 * An interface for managing and getting information about {@code Task} objects.
 */
public interface TaskService extends ResourceService<Task> {

  /**
   * Returns the {@code Task}'s associated with the given {@code Project}.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the {@code id} of the {@code Project} to get the tasks for
   * @return the {@code Task}'s associated with the given {@code Project}
   *         or null if the given {@code projectId} is not associated with the {@code userName} or it is not exists
   */
  Collection<Task> getTasks(String userName, int projectId);

  /**
   * Returns all the income for the task with the given hourly wage.
   *
   * @param userName the user
   * @param taskId the id of the task
   * @param hourlyWage the hourly wage for the project
   * @return the income from the task
   */
  Integer getAllIncome(String userName, int taskId, int hourlyWage);

  /**
   * Returns the time spent with working on this task in seconds.
   *
   * @param userName the user
   * @param taskId the id of the task
   * @return the time spent on the task
   */
  Integer getAllWorkedTime(String userName, int taskId);

  /**
   * Returns all the income from the task, by getting the hourly wage from the project.
   *
   * @param userName the user
   * @param taskId the id of the task
   * @return the income from the task
   */
  Integer getAllIncome(String userName, int taskId);
}
