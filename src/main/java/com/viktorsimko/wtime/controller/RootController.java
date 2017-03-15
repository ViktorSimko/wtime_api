package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by simkoviktor on 2017. 03. 15..
 */
@RestController
public class RootController {
  @Autowired
  private ProjectService projectService;

  @GetMapping("/")
  public String getRoot() {
    return "WTime";
  }

  @GetMapping("/projects")
  public Collection<Project> getProjects() {
    return projectService.getProjects();
  }
}
