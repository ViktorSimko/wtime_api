package com.viktorsimko.wtime.test;

import com.viktorsimko.wtime.model.WorkInterval;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkIntervalTest {

  WorkInterval workInterval = new WorkInterval();

  @Test
  public void test__getDuration__when_begin_and_end_null__should_return_duration_zero() {
    Duration returnedDuration = workInterval.getDuration();
    assertThat(returnedDuration, is(equalTo(Duration.ZERO)));
  }

  @Test
  public void test__getDuration__when_begin_null__should_return_duration_zero() {
    workInterval.setEnd(LocalDateTime.now());
    Duration returnedDuration = workInterval.getDuration();
    assertThat(returnedDuration, is(equalTo(Duration.ZERO)));
  }
  @Test
  public void test__getDuration__when_end_null__should_return_duration_zero() {
    workInterval.setBegin(LocalDateTime.now());
    Duration returnedDuration = workInterval.getDuration();
    assertThat(returnedDuration, is(equalTo(Duration.ZERO)));
  }

  @Test
  public void test__getDuration__when_end_not_null_and_begin_not_null__should_return_the_duration_between_them() {
    LocalDateTime begin = LocalDateTime.now();
    LocalDateTime end = begin.plusMinutes(1);

    workInterval.setBegin(begin);
    workInterval.setEnd(end);
    Duration returnedDuration = workInterval.getDuration();
    assertThat(returnedDuration, is(equalTo(Duration.ofMinutes(1))));
  }
}
