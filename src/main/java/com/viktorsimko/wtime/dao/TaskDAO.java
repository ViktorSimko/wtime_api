package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Task;

import java.util.Collection;

/**
 * Interface for accessing and manipulating the tasks in the database.
 */
public interface TaskDAO {

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
   * @return the saved {@code Task}, if the save was successful, null else
   */
  Task addTask(Task task);

  /**
   * Updates the given {@code Task}.
   *
   * @param userName the owner of the {@code Task}
   * @param taskId the id of the {@code Task} to update
   * @param updateInfo the information to update the {@code Task} with
   * @return the updated {@code Task}, if the update was successful, else null
   */
  Task updateTask(String userName, int taskId, Task updateInfo);

  /**
   * Deletes the given {@code Task}.
   *
   * @param userName the owner of the {@code Task}
   * @param taskId the {@code id} of the {@code Task} to delete
   * @return the deleted {@code Task}, if the deletion was successful, else null
   */
  Task deleteTask(String userName, int taskId);

}
