package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.dao.TaskDAO;
import com.viktorsimko.wtime.dao.WorkIntervalDAO;
import com.viktorsimko.wtime.model.Task;
import com.viktorsimko.wtime.model.WorkInterval;
import com.viktorsimko.wtime.service.WorkIntervalServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkIntervalServiceImplTest {

  @Mock
  private WorkInterval workInterval;

  class TestableWorkIntervalServiceImpl extends WorkIntervalServiceImpl {
    @Override
    public WorkInterval getResource(String userName, int resourceId) {
      if (userName == null) return null;
      return workInterval;
    }
  }

  @InjectMocks
  private WorkIntervalServiceImpl workIntervalService = new TestableWorkIntervalServiceImpl();

  @Mock
  private TaskDAO taskDAO;

  @Mock(name = "resourceDAO")
  private WorkIntervalDAO workIntervalDAO;

  private String  userName = "johndoe";
  private int workIntervalId = 0;
  private int taskId = 1;

  @Before
  public void setUp() {
    when(workInterval.getTaskId()).thenReturn(taskId);
    when(workInterval.getUser()).thenReturn(userName);
    when(workInterval.getDuration()).thenReturn(Duration.ofMinutes(1));
  }

  @Test
  public void test__getWorkIntervals__when_task_not_found__should_return_null() {
    Collection<WorkInterval> workIntervals = workIntervalService.getWorkIntervals(userName, taskId);
    assertThat(workIntervals, is(nullValue()));
  }

  @Test
  public void test__getWorkIntervals__when_task_found__should_return_the_intervals_for_the_task() throws Exception {
    when(taskDAO.getResource(userName, taskId)).thenReturn(new Task());

    WorkInterval workInterval1 = new WorkInterval();
    TestUtil.setIdOnResource(workInterval1, 1);

    WorkInterval workInterval2 = new WorkInterval();
    TestUtil.setIdOnResource(workInterval2, 2);

    Collection<WorkInterval> workIntervals = Arrays.asList(workInterval1, workInterval2);

    when(workIntervalDAO.getWorkIntervals(userName, taskId)).thenReturn(workIntervals);

    Collection<WorkInterval> returnedWorkIntervals = workIntervalService.getWorkIntervals(userName, taskId);
    assertThat(returnedWorkIntervals, hasSize(2));
    assertThat(returnedWorkIntervals, is(equalTo(workIntervals)));
  }

  @Test
  public void test__addResource__when_task_not_found__should_return_null() {
    WorkInterval returnedWorkInterval = workIntervalService.addResource(workInterval);
    assertThat(returnedWorkInterval, is(nullValue()));
  }

  @Test
  public void test__addResource__when_task_is_found__should_return_what_workIntervalDAO_returns() {
    when(taskDAO.getResource(userName, taskId)).thenReturn(new Task());
    when(workIntervalDAO.addResource(workInterval)).thenReturn(workInterval);

    WorkInterval returnedWorkInterval = workIntervalService.addResource(workInterval);
    assertThat(returnedWorkInterval, is(equalTo(workInterval)));
  }

  @Test
  public void test__updateResource__when_task_is_not_found__should_return_null() {
    WorkInterval returnedWorkInterval = workIntervalService.updateResource(userName, workIntervalId, workInterval);
    assertThat(returnedWorkInterval, is(nullValue()));
  }

  @Test
  public void test__updateResource__when_taskId_is_null__should_return_null() {
    when(workInterval.getTaskId()).thenReturn(null);
    WorkInterval returnedWorkInterval = workIntervalService.updateResource(userName, workIntervalId, workInterval);
    assertThat(returnedWorkInterval, is(nullValue()));
  }

  @Test
  public void test__test_updateResource__when_taskId_is_null_and_task_not_found__should_return_null() {
    when(workInterval.getTaskId()).thenReturn(null);
    WorkInterval returnedWorkInterval = workIntervalService.updateResource(userName, workIntervalId, workInterval);
    assertThat(returnedWorkInterval, is(nullValue()));
  }

  @Test
  public void test__updateResource__when_taskId_is_valid_and_task_is_found__should_return_what_workIntervalDAO_returns() {
    when(taskDAO.getResource(userName, taskId)).thenReturn(new Task());
    when(workIntervalDAO.updateResource(userName, workIntervalId, workInterval)).thenReturn(workInterval);

    WorkInterval returnedWorkInterval = workIntervalService.updateResource(userName, workIntervalId, workInterval);
    assertThat(returnedWorkInterval, is(equalTo(workInterval)));
  }

  @Test
  public void test__getAllWorkedTime__when_work_interval_not_found__should_return_null() {
    Integer allWorkedTime = workIntervalService.getAllWorkedTime(null, workIntervalId);
    assertThat(allWorkedTime, is(nullValue()));
  }

  @Test
  public void test__getAllWorkedTime__when_work_interval_found_and_duration_not_null__should_return_duration_in_seconds() {
    Integer allWorkedTime = workIntervalService.getAllWorkedTime(userName, workIntervalId);
    assertThat(allWorkedTime, is(equalTo(60)));
  }

  @Test
  public void test__getAllIncome__when_workInterval_not_found__should_return_null() {
    Integer allIncome = workIntervalService.getAllIncome(null, workIntervalId, 1100);
    assertThat(allIncome, is(nullValue()));
  }

  @Test
  public void test__getAllIncome__when_workInterval_found_and_duration_not_null__should_return_the_income_for_the_duration() {
    Integer allIncome = workIntervalService.getAllIncome(userName, workIntervalId, 600);
    assertThat(allIncome, is(equalTo(10)));
  }
}
