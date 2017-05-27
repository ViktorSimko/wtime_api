package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * A service for managing and getting information about {@code Project} objects.
 */
public interface ProjectService extends ResourceService<Project> {

  /**
   * Returns all the income from the project.
   *
   * @param userName the user
   * @param projectId the id of the project
   * @return the income from the given project
   */
  Integer getAllIncome(String userName, int projectId);

  /**
   * Returns the time spent on the project in seconds.
   *
   * @param userName the user
   * @param projectId the id of the project
   * @return the time spent on this project
   */
  Integer getAllWorkedTime(String userName, int projectId);

}

