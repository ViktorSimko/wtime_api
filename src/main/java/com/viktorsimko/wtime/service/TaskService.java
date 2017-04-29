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

  Duration allWorkedTimeForProject(String userName, int projectId);

  int allIncomeForProject(String userName, int projectId, int hourlyWage);
}
