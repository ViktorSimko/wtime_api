package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Resource;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.service.ProjectServiceImpl;
import com.viktorsimko.wtime.service.ResourceServiceImpl;
import com.viktorsimko.wtime.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

  private Project project;
  private Collection<Task> tasks;

  class TestableProjectServiceImpl extends ProjectServiceImpl {
    @Override
    public Project getResource(String userName, int resourceId) {
      if (userName == null) return null;
      return project;
    }
  }

  @InjectMocks
  private ProjectServiceImpl projectService = new TestableProjectServiceImpl();

  @Mock
  private ProjectDAO projectDAO;

  @Mock
  private TaskService taskService;

  private String userName = "johndoe";
  private int projectId = 0;

  @Before
  public void setUp() throws Exception {
    project = new Project();
    project.setHourlyWage(1500);

    tasks = new ArrayList<>();

    Task task1 = new Task();
    TestUtil.setIdOnResource(task1, 1);
    tasks.add(task1);

    Task task2 = new Task();
    TestUtil.setIdOnResource(task2, 2);
    tasks.add(task2);
  }

  @Test
  public void test__getAllIncome__should_sum_all_the_income_from_the_tasks_and_return_it() {
    when(taskService.getTasks(userName, projectId)).thenReturn(tasks);
    when(taskService.getAllIncome(userName, 1, project.getHourlyWage())).thenReturn(200);
    when(taskService.getAllIncome(userName, 2, project.getHourlyWage())).thenReturn(300);
    int sumOfIncome = projectService.getAllIncome(userName, projectId);
    assertThat(sumOfIncome, is(equalTo(500)));
  }

  @Test
  public void test_getAllIncome__returns_null_when_project_not_found() {
    Integer sumOfIncome = projectService.getAllIncome(null, projectId);
    assertTrue(sumOfIncome == null);
  }

  @Test
  public void test__getAllWorkedTime__should_sum_all_the_worked_times_for_the_tasks() {
    when(taskService.getTasks(userName, projectId)).thenReturn(tasks);
    when(taskService.getAllWorkedTime(userName, 1)).thenReturn(3000);
    when(taskService.getAllWorkedTime(userName, 2)).thenReturn(1000);
    int sumOfWorkedTime = projectService.getAllWorkedTime(userName, projectId);
    assertThat(sumOfWorkedTime, is(equalTo(4000)));
  }

  @Test
  public void test_getAllWorkedTime__returns_null_when_project_not_found() {
    Integer sumOfWorkedTime = projectService.getAllWorkedTime(null, projectId);
    assertTrue(sumOfWorkedTime == null);
  }

  @Test
  public void test__deleteResource__when_project_not_found__should_return_null() {
    when(taskService.getTasks(userName, projectId)).thenReturn(null);
    Project returnedProject = projectService.deleteResource(userName, projectId);
    assertThat(returnedProject, is(nullValue()));
  }

  @Test
  public void test__deleteResource__should_delete_all_the_tasks_of_the_project() {
    when(taskService.getTasks(userName, projectId)).thenReturn(tasks);

    projectService.deleteResource(userName, projectId);

    verify(taskService).deleteResource(userName, 1);
    verify(taskService).deleteResource(userName, 2);
  }

  @Test
  public void test__deleteResource__should_return_what_projectDAO_deleteResource_returns() {
    when(projectDAO.deleteResource(userName, projectId)).thenReturn(project);

    Project returnedProject = projectService.deleteResource(userName, projectId);
    assertTrue(returnedProject == project);
  }
}
