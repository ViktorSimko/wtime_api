package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.WorkInterval;

import java.util.Collection;

/**
 * A service for managing and getting information about {@code WorkTimeInterval} objects.
 */
public interface WorkIntervalService {

  /**
   * Returns all the {@code WorkTimeInterval}'s associated with the given task.
   *
   * @param userName the {@code name} of the current user
   * @param taskId the id of the task, which we want to get the {@code WorkTimeInterval}'s for
   * @return the {@code WorkTimeInterval}'s associated with the given task
   */
  Collection<WorkInterval> getWorkIntervals(String userName, int taskId);

  /**
   * Returns all the {@code WorkTimeInterval}'s associated with the given user.
   *
   * @param userName the {@code name} of the current user
   * @return the {@code WorkTimeInterval}'s associated with the given user
   */
  Collection<WorkInterval> getWorkIntervals(String userName);

  /**
   * Returns the {@code WorkTimeInterval} object for the given id.
   *
   * @param userName the {@code name} of the current user
   * @param workIntervalId the {@code id} of the {@code WorkTimeInterval} object we want to get
   * @return the {@code WorkTimeInterval} object for the given id,
   *         or null if the given {@code id} is not associated with the {@code userName} or it is not exists
   */
  WorkInterval getWorkInterval(String userName, int workIntervalId);

  /**
   * Adds the given {@code WorkInterval} to the database.
   *
   * @param workInterval the {@code WorkInterval} to save
   */
  void addWorkInterval(WorkInterval workInterval);
}
