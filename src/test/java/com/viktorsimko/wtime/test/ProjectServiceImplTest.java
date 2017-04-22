package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.service.ProjectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

  @InjectMocks
  private ProjectServiceImpl projectService = new ProjectServiceImpl();

  @Mock
  private ProjectDAO projectDAO;

  private String userName = "johndoe";
  private int projectId = 0;

  @Test
  public void test__getProjects__calls_ProjectDAO_getProjects_with_the_same_username_as_argument() {
    projectService.getProjects(userName);

    verify(projectDAO).getProjects(userName);
  }

  @Test
  public void test__getProjects__returns_the_value_that_ProjectDAO_getProjects_returns() {
    List<Project> projects = Arrays.asList(new Project(), new Project());

    when(projectDAO.getProjects(userName)).thenReturn(projects);

    List<Project> returnedProjects = projectService.getProjects(userName);

    assertTrue(returnedProjects == projects);
  }

  @Test
  public void test__addProject__calls_ProjectDAO_addProject_with_the_same_project_as_argument() {
    Project project = new Project();

    projectService.addProject(project);

    verify(projectDAO).addProject(project);
  }

  @Test
  public void test__getProject__calls_ProjectDAO_getProject_with_the_same_username_and_project_id() {

    projectService.getProject(userName, projectId);

    verify(projectDAO).getProject(userName, projectId);
  }

  @Test
  public void test__getProject__returns_the_same_project_as_ProjectDAO_getProject() {
    Project project = new Project();

    when(projectDAO.getProject(userName, projectId)).thenReturn(project);

    Project returnedProject = projectService.getProject(userName, projectId);

    assertTrue(project == returnedProject);
  }
}