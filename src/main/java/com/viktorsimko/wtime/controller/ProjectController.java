package com.viktorsimko.wtime.controller;

import static com.viktorsimko.wtime.util.ResourceChecker.checkResource;
import static com.viktorsimko.wtime.util.ResourceChecker.checkResourceCreated;

import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * The rest controller for handling projects.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

  private Logger logger = LoggerFactory.getLogger(ProjectController.class);

  @Autowired
  private ProjectService projectService;

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

    logger.debug("POST /projects user: {}", userName);

    return checkResourceCreated(addedProject);
  }

  /**
   * Get all the projects for the authenticated user.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @return all the projects for the authenticated user with a response
   */
  @GetMapping
  public ResponseEntity<Collection<Project>> getProjects(Authentication authentication) {
    String userName = authentication.getName();

    logger.debug("GET /projects user: {}", userName);

    Collection<Project> projects = projectService.getResources(userName);

    return checkResource(projects);
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

    logger.debug("GET /projects/{} user: {}", projectId, userName);

    return checkResource(project);
  }

  /**
   * Returns all the income from the project, or null if the project not found.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project
   * @return all the income from the project, or null if the project not found
   */
  @GetMapping("/{projectId}/allIncome")
  public ResponseEntity<Integer> getAllIncomeForProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Integer allIncome = projectService.getAllIncome(userName, projectId);

    logger.debug("GET /projects/{}/allIncome user: {}", projectId, userName);

    return checkResource(allIncome);
  }

  /**
   * Returns the sum of time spent working on the project.
   *
   * @param authentication the authentication object passed in by Spring Security
   * @param projectId the id of the project
   * @return the sum of time spent with the project
   */
  @GetMapping("/{projectId}/allWorkedTime")
  public ResponseEntity<Integer> getAllWorkedTimeForProject(Authentication authentication, @PathVariable("projectId") int projectId) {
    String userName = authentication.getName();
    Integer allWorkedTime = projectService.getAllWorkedTime(userName, projectId);

    logger.debug("GET /projects/{}/allWorkedTime user: {}", projectId, userName);

    return checkResource(allWorkedTime);
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

    logger.debug("PATCH /projects/{} user: {}", projectId, userName);

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

    logger.debug("DELETE /projects/{} user: {}", projectId, userName);

    return checkResource(deletedProject);
  }
}
