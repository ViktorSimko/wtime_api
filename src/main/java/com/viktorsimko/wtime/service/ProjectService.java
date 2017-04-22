package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * A service for managing and getting information about {@code Project} objects.
 */
public interface ProjectService {

  /**
   * Returns the {@code Project}'s associated with the given user.
   *
   * @param userName the {@code name} of the current user
   * @return the {@code Project}'s associated with the given user
   */
  List<Project> getProjects(String userName);

  /**
   * Returns the {@code Project} for the given {@code projectId}.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the id of the {@code Project} to get
   * @return the {@code Project}'s associated with the given {@code projectId},
   *         or null if the given {@code id} is not associated with the {@code userName} or it is not exists
   */
  Project getProject(String userName, int projectId);

  /**
   * Saves the given {@code Project} to the database.
   *
   * @param project the {@code Project} to save
   */
  Project addProject(Project project);

  /**
   * Updates the project.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the id of the project to update
   * @param updatedProjectInfo the information to update the project with
   * @return the updated project
   */
  Project updateProject(String userName, int projectId, Project updatedProjectInfo);

  /**
   * Deletes the given project.
   *
   * @param userName the {@code name} of the current user
   * @param projectId the id of the project to delete
   * @return the deleted project
   */
  Project deleteProject(String userName, int projectId);
}
