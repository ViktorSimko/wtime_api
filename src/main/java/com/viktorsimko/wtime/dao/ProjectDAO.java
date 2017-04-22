package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * Created by simkoviktor on 2017. 03. 15..
 */
public interface ProjectDAO {

  List<Project> getProjects(String userName);

  Project addProject(Project project);

  Project getProject(String userName, int projectId);

  Project updateProject(String userName, int projectId, Project updatedProjectInfo);

  Project deleteProject(String userName, int projectId);
}
