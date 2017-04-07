package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectDAO projectDAO;

  @Override
  @Transactional
  public List<Project> getProjects(String userName) {
    return projectDAO.getProjects(userName);
  }

  @Override
  @Transactional
  public void addProject(Project project) {
    projectDAO.addProject(project);
  }

  @Override
  @Transactional
  public Project getProject(String userName, int projectId) {
    return projectDAO.getProject(userName, projectId);
  }
}
