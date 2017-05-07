package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * A service for managing and getting information about {@code Project} objects.
 */
public interface ProjectService extends ResourceService<Project> {

  Integer getAllIncome(String userName, int projectId);

  Integer getAllWorkedTime(String userName, int projectId);

}

