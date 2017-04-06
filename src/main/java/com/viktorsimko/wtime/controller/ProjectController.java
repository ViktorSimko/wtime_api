package com.viktorsimko.wtime.controller;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTMLDocument;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by simkoviktor on 2017. 03. 18..
 *
 * The rest controller for handling projects.
 *
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

  /**
   * The project service, by which we interact with the model
   */
  @Autowired
  private ProjectService projectService;

  /**
   * Get all the projects for the authenticated user
   *
   * @param authentication The authentication object passed in by Spring Security
   * @return All the projects for the authenticated user
   */
  @GetMapping
  public Collection<Project> getProjects(Authentication authentication) {
    String userName = authentication.getName();
    return projectService.getProjects(userName);
  }

  /**
   * Adds the project for the authenticated user
   *
   * @param authentication The authentication object passed in by Spring Security
   * @param project The project from the request body
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void postProject(Authentication authentication, @RequestBody Project project) {
    String userName = authentication.getName();
    project.setUser(userName);

    System.out.println(project.getTasks());
    projectService.addProject(project);
  }

  /**
   * Returns a project for the given projectId
   *
   * @param projectId The id of the project we want get
   * @return The project for the given projectId
   */
  @Transactional
  @GetMapping("/{projectId}")
  public Project getProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getProject(userName, projectId);

    return checkResource(project);
  }

  @GetMapping(value = "/{projectId}/tasklist", produces = "application/json")
  public String getTaskListForProject(Authentication authentication,
                                      @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getProject(userName, projectId);
    checkResource(project);

    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append("{");
    responseBuilder.append("\"tasks\":");
    responseBuilder.append("[");

    Iterator<Task> taskIterator = project.getTasks().iterator();

    while (true) {
      Task task = taskIterator.next();

      responseBuilder.append(task.getId());

      if (taskIterator.hasNext()) {
        responseBuilder.append(",");
      } else {
        break;
      }
    }

    responseBuilder.append("]");
    responseBuilder.append("}");

    return responseBuilder.toString();
  }
}
