package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.dao.ProjectDAO;
import com.viktorsimko.wtime.dao.ResourceDAO;
import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.model.Project;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.model.WorkInterval;
import com.viktorsimko.wtime.service.TaskService;
import com.viktorsimko.wtime.service.TaskServiceImpl;
import com.viktorsimko.wtime.service.WorkIntervalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

  private Task task;
  private Collection<WorkInterval> workIntervals;
  private Collection<Task> tasks;

  class TestableTaskServiceImpl extends TaskServiceImpl {
    @Override
    public Task getResource(String userName, int resourceId) {
      if (userName == null) return null;
      return task;
    }
  }

  @InjectMocks
  TaskServiceImpl taskService = new TestableTaskServiceImpl();

  @Mock
  private WorkIntervalService workIntervalService;

  @Mock
  private ProjectDAO projectDAO;

  @Mock(name = "resourceDAO")
  private TaskDAO taskDAO;

  private String userName = "johndoe";
  private int taskId = 0;
  private int projectId = 3;

  @Before
  public void setUp() throws Exception {
    task = new Task();
    task.setBonus(200);
    task.setUser(userName);
    task.setProjectId(projectId);

    workIntervals = new ArrayList<>();

    WorkInterval workInterval1 = new WorkInterval();
    TestUtil.setIdOnResource(workInterval1, 1);
    workIntervals.add(workInterval1);

    WorkInterval workInterval2 = new WorkInterval();
    TestUtil.setIdOnResource(workInterval2, 2);
    workIntervals.add(workInterval2);

    WorkInterval workInterval3 = new WorkInterval();
    TestUtil.setIdOnResource(workInterval3, 3);
    workIntervals.add(workInterval3);

    tasks = new ArrayList<>();

    Task task1 = new Task();
    task1.setUser(userName);
    tasks.add(task1);

    Task task2 = new Task();
    task2.setUser(userName);
    tasks.add(task2);
  }

  @Test
  public void test__getTasks__when_the_project_not_exists__should_return_null() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(null);

    Collection<Task> returnedTasks = taskService.getTasks(userName, projectId);
    assertThat(returnedTasks, is(nullValue()));
  }

  @Test
  public void test__getTasks__when_project_is_found__should_return_what_taskDAO_returns() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(new Project());
    when(taskDAO.getTasks(userName, projectId)).thenReturn(tasks);

    Collection<Task> returnedTasks = taskService.getTasks(userName, projectId);
    assertThat(returnedTasks, hasSize(2));
    assertThat(returnedTasks, is(equalTo(tasks)));
  }

  @Test
  public void test__addResource__when_project_not_found__should_return_null() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(null);

    Task returnedTask = taskService.addResource(task);
    assertThat(returnedTask, is(nullValue()));
  }

  @Test
  public void test__addResource__when_project_found__should_return_what_taskDAO_returns() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(new Project());
    when(taskDAO.addResource(task)).thenReturn(task);

    Task returnedTask = taskService.addResource(task);
    assertThat(returnedTask, is(equalTo(task)));
  }

  @Test
  public void test__updateResource__when_projectId_is_null__should_return_null() {
    task.setProjectId(null);

    Task returnedTask = taskService.updateResource(userName, taskId, task);
    assertThat(returnedTask, is(nullValue()));
  }

  @Test
  public void test__updateResource__when_project_not_found__should_return_null() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(null);

    Task returnedTask = taskService.updateResource(userName, taskId, task);
    assertThat(returnedTask, is(nullValue()));
  }

  @Test
  public void test__updateResource__when_project_found__should_return_what_taskDAO_returns() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(new Project());
    when(taskDAO.updateResource(userName, taskId, task)).thenReturn(task);

    Task returnedTask = taskService.updateResource(userName, taskId, task);
    assertThat(returnedTask, is(equalTo(task)));
  }

  @Test
  public void test__deleteResource__when_there_are_work_intervals_of_that_task__should_delete_them() {
    when(workIntervalService.getWorkIntervals(userName, taskId)).thenReturn(workIntervals);

    taskService.deleteResource(userName, taskId);

    verify(workIntervalService).deleteResource(userName, 1);
    verify(workIntervalService).deleteResource(userName, 2);
  }

  @Test
  public void test__deleteResource__should_return_what_taskDAO_returns() {
    when(taskDAO.deleteResource(userName, taskId)).thenReturn(task);

    Task returnedTask = taskService.deleteResource(userName, taskId);
    assertThat(returnedTask, is(equalTo(task)));
  }

  @Test
  public void test__getAllWorkedTime__when_task_not_exists__should_return_null() {
    Integer allWorkedTime = taskService.getAllWorkedTime(null, taskId);
    assertThat(allWorkedTime, is(nullValue()));
  }

  @Test
  public void test__getAllWorkedTime__when_task_exists__should_return_the_sum_of_the_work_time() {
    when(workIntervalService.getWorkIntervals(userName, taskId)).thenReturn(workIntervals);
    when(workIntervalService.getAllWorkedTime(userName, 1)).thenReturn(500);
    when(workIntervalService.getAllWorkedTime(userName, 2)).thenReturn(600);
    when(workIntervalService.getAllWorkedTime(userName, 3)).thenReturn(400);

    Integer allWorkedTime = taskService.getAllWorkedTime(userName, taskId);
    assertThat(allWorkedTime, is(equalTo(1500)));
  }

  @Test
  public void test__getAllIncome_with_hourly_wage__when_task_not_found__should_return_null() {
    Integer allIncome = taskService.getAllIncome(null, taskId, 1100);
    assertThat(allIncome, is(nullValue()));
  }

  @Test
  public void test__getAllIncome_with_hourly_wage__when_task_found__should_return_all_income() {
    when(workIntervalService.getWorkIntervals(userName, taskId)).thenReturn(workIntervals);
    when(workIntervalService.getAllIncome(userName, 1, 1100)).thenReturn(400);
    when(workIntervalService.getAllIncome(userName, 2, 1100)).thenReturn(500);
    when(workIntervalService.getAllIncome(userName, 3, 1100)).thenReturn(600);

    Integer allIncome = taskService.getAllIncome(userName, taskId, 1100);
    assertThat(allIncome, is(equalTo(2100)));
  }
  
  @Test
  public void test__getAllIncome__when_task_not_found__should_return_null() {
    Integer allIncome = taskService.getAllIncome(null, taskId);
    assertThat(allIncome, is(nullValue()));
  }

  @Test
  public void test__getAllIncome__when_project_not_found__should_return_null() {
    when(projectDAO.getResource(userName, projectId)).thenReturn(null);

    Integer allIncome = taskService.getAllIncome(userName, taskId);
    assertThat(allIncome, is(nullValue()));
  }

  @Test
  public void test__getAllIncome__when_task_and_project_found__should_return_all_income() {
    Project project = new Project();
    project.setHourlyWage(1100);

    when(projectDAO.getResource(userName, projectId)).thenReturn(project);
    when(workIntervalService.getWorkIntervals(userName, taskId)).thenReturn(workIntervals);
    when(workIntervalService.getAllIncome(userName, 1, 1100)).thenReturn(400);
    when(workIntervalService.getAllIncome(userName, 2, 1100)).thenReturn(500);
    when(workIntervalService.getAllIncome(userName, 3, 1100)).thenReturn(600);

    Integer allIncome = taskService.getAllIncome(userName, taskId);

    assertThat(allIncome, is(equalTo(2100)));
  }
}
