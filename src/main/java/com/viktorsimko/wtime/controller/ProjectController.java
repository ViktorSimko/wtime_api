package com.viktorsimko.wtime.controller;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;
import static com.viktorsimko.wtime.util.ResourceChecker.checkResourceCreated;

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
   * @return all the projects for the authenticated user with a response
   */
  @GetMapping
  public ResponseEntity<Collection<Project>> getProjects(Authentication authentication) {
    String userName = authentication.getName();

    Collection<Project> projects = projectService.getResources(userName);

    return checkResource(projects);
  }

  /**
   * Adds the project for the authenticated user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param project the project from the request body
   * @return a status 201 response containing the uploaded project if the upload was successful,
   *         else a status 400 response
   */
  @PostMapping
  public ResponseEntity<Project> postProject(Authentication authentication, @RequestBody Project project) {
    String userName = authentication.getName();
    project.setUser(userName);

    Project addedProject = projectService.addResource(project);

    return checkResourceCreated(addedProject);
  }

  /**
   * Returns a project for the given projectId.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project we want get
   * @return a response containing the project with a status code of 200 if it exists,
   *         a response with a status of 404 else
   */
  @GetMapping("/{projectId}")
  public ResponseEntity<Project> getProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project project = projectService.getResource(userName, projectId);

    return checkResource(project);
  }

  /**
   * Updates a project.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project to update
   * @param updatedProjectInfo the information to update the project with
   * @return a response containing the updated project with a status code of 200 if the update was successful,
   *         a response with a status of 404 else
   */
  @PatchMapping("/{projectId}")
  public ResponseEntity<Project> patchProject(Authentication authentication, @PathVariable("projectId") int projectId, @RequestBody Project updatedProjectInfo) {
    String userName = authentication.getName();

    Project updatedProject = projectService.updateResource(userName, projectId, updatedProjectInfo);

    return checkResource(updatedProject);
  }

  /**
   * Deletes the project with the given id.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project to delete
   * @return a response containing the deleted project with a status code of 200 if the delete was successful,
   *         a response with a status of 404 else
   */
  @DeleteMapping("/{projectId}")
  public ResponseEntity<Project> deleteProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Project deletedProject = projectService.deleteResource(userName, projectId);

    return checkResource(deletedProject);
  }
}
