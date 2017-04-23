package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service for managing project resources.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ResourceDAO<Project> projectDAO;

  @Override
  @Transactional
  public List<Project> getProjects(String userName) {
    return projectDAO.getResources(userName);
  }

  @Override
  @Transactional
  public Project addProject(Project project) {
    return projectDAO.addResource(project);
  }

  @Override
  @Transactional
  public Project getProject(String userName, int projectId) {
    return projectDAO.getResource(userName, projectId);
  }

  @Override
  @Transactional
  public Project updateProject(String userName, int projectId, Project updatedProjectInfo) {
    return projectDAO.updateResource(userName, projectId, updatedProjectInfo);
  }

  @Override
  @Transactional
  public Project deleteProject(String userName, int projectId) {
    return projectDAO.deleteResource(userName, projectId);
  }
}
