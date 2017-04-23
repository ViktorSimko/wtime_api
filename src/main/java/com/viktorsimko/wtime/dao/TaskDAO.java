package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;

import java.util.Collection;

/**
 * Interface for accessing and manipulating the tasks in the database.
 */
public interface TaskDAO extends ResourceDAO<Task> {

  /**
   * Returns the {@code Task}'s associated with the given {@code Project}.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the {@code id} of the {@code Project} to get the tasks for
   * @return the {@code Task}'s associated with the given {@code Project}
   *         or null if the given {@code projectId} is not associated with the {@code userName} or it is not exists
   */
  Collection<Task> getTasks(String userName, int projectId);

}
