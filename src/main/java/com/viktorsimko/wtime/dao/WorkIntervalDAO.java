package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.WorkInterval;

import java.util.Collection;

/**
 * A {@code ResourceDAO} with {@code WorkInterval} specific methods.
 */
public interface WorkIntervalDAO extends ResourceDAO<WorkInterval> {

  /**
   * Returns the work intervals for the given task.
   *
   * @param userName the user
   * @param taskId the task id
   * @return the work intervals for the task
   */
  Collection<WorkInterval> getWorkIntervals(String userName, int taskId);

}
