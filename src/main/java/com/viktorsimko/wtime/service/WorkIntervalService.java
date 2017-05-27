package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.WorkInterval;

import java.time.Duration;
import java.util.Collection;

/**
 * A service for managing and getting information about {@code WorkTimeInterval} objects.
 */
public interface WorkIntervalService extends ResourceService<WorkInterval> {

  /**
   * Returns the work intervals for the given task.
   * @param userName the user
   * @param taskId the id of the task
   * @return the work intervals for the task, or null if the task is not found
   */
  Collection<WorkInterval> getWorkIntervals(String userName, int taskId);

  /**
   * Returns the duration of the work interval in seconds.
   *
   * @param userName the user
   * @param workIntervalId the id of the work interval
   * @return the duration of the work interval in seconds
   */
  Integer getAllWorkedTime(String userName, int workIntervalId);

  /**
   * Returns all the income from the work interval.
   *
   * @param userName the user
   * @param workIntervalId the id of the work interval
   * @param hourlyWage the hourly wage for the project
   * @return the income from the work interval
   */
  Integer getAllIncome(String userName, int workIntervalId, int hourlyWage);
}
