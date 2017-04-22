package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * Interface for accessing and manipulating the projects in the database.
 */
public interface ProjectDAO {

  /**
   * Returns the projects for the given user from the database.
   *
   * @param userName the owner of the projects
   * @return a list of the projects of the user
   */
  List<Project> getProjects(String userName);

  /**
   * Adds the given project to the database.
   *
   * @param project the {@code Project} to save
   * @return the saved {@code Project} if the save was successful, else null
   */
  Project addProject(Project project);

  /**
   * Get the project for a given id.
   *
   * @param userName the owner of the project
   * @param projectId the id of the project
   * @return the project associated with {@code userName} and {@code projectId},
   *         or null if a project with the given {@code projectId} and {@code userName} not exists
   */
  Project getProject(String userName, int projectId);

  /**
   * Update the project with the given id using the given information.
   *
   * @param userName the owner of the project
   * @param projectId the id of the project
   * @param updatedProjectInfo the information to update the project with
   * @return the updated {@code Project}, if the update was successful, else null
   */
  Project updateProject(String userName, int projectId, Project updatedProjectInfo);

  /**
   * Deletes the given project.
   *
   * @param userName the owner of the project
   * @param projectId the id of the project
   * @return the deleted project if the deletion was successful, else null
   */
  Project deleteProject(String userName, int projectId);
}
