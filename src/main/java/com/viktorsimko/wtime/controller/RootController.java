package com.viktorsimko.wtime.controller;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Collection<Project> getProjects(Authentication authentication) {
    String userName = authentication.getName();
    System.out.println(authentication.getName());
    return projectService.getProjects(userName);
  }

  @PostMapping("/project")
  public void addProject(Authentication authentication, @RequestBody Project project) {
    String userName = authentication.getName();
    System.out.println(authentication.getName());
    project.setUser(userName);
    projectService.addProject(project);
  }

}
