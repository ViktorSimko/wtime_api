package com.viktorsimko.wtime.controller;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

/**
 * The rest controller for handling projects.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  /**
   * Get all the projects for the authenticated user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @return all the projects for the authenticated user
   */
  @GetMapping
  public ResponseEntity<Collection<Project>> getProjects(Authentication authentication) {
    String userName = authentication.getName();

    Collection<Project> projects = projectService.getProjects(userName);

    return ResponseEntity.ok(projects);
  }

  /**
   * Adds the project for the authenticated user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param project the project from the request body
   */
  @PostMapping
  public ResponseEntity<Project> postProject(Authentication authentication, @RequestBody Project project) {
    String userName = authentication.getName();
    project.setUser(userName);

    Project addedProject = projectService.addProject(project);

    return ResponseEntity.created(URI.create("/projects/" + project.getId())).body(addedProject);
  }

  /**
   * Returns a project for the given projectId.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project we want get
   * @return the project for the given projectId
   */
  @GetMapping("/{projectId}")
  public ResponseEntity<Project> getProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getProject(userName, projectId);

    if (project == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(project);
  }

  /**
   * Updates a project.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project to update
   * @return the updated project
   */
  @PatchMapping("/{projectId}")
  public ResponseEntity<Project> patchProject(Authentication authentication, @PathVariable("projectId") int projectId, @RequestBody Project updatedProjectInfo) {
    String userName = authentication.getName();

    Project updatedProject = projectService.updateProject(userName, projectId, updatedProjectInfo);

    return ResponseEntity.ok(updatedProject);
  }

  /**
   * Deletes the project with the given id.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project to delete
   * @return the deleted project
   */
  @DeleteMapping("/{projectId}")
  public ResponseEntity<Project> deleteProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getProject(userName, projectId);

    return checkResource(project);
  }

  /*@GetMapping(value = "/{projectId}/tasklist", produces = "application/json")
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
  }*/
}
