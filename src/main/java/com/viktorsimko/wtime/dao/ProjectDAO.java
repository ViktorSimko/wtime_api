package com.viktorsimko.wtime.dao;

import com.viktorsimko.wtime.model.Project;

import java.util.List;

/**
 * Created by simkoviktor on 2017. 03. 15..
 */
public interface ProjectDAO {

  List<Project> getProjects(String userName);

  void addProject(Project project);

  Project getProject(String userName, int projectId);
}
