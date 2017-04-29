package com.viktorsimko.wtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This class represents an interval of work done by the user on a given task.
 */
@Entity
@Table(name = "work_interval")
public class WorkInterval extends Resource {

  @NotNull
  @Column(name = "task_id")
  private Integer taskId;

  @NotNull
  @Column(name = "begin")
  private LocalDateTime begin;

  @Column(name = "end")
  private LocalDateTime end;

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public LocalDateTime getBegin() {
    return begin;
  }

  public void setBegin(LocalDateTime begin) {
    this.begin = begin;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public Duration getDuration() {
    return (begin == null || end == null) ? Duration.ZERO : Duration.ofSeconds(ChronoUnit.SECONDS.between(begin, end));
  }

  public int getIncome(int hourlyWage) {
    return getDuration() == null ? 0 : (int) Math.round(getDuration().getSeconds() / 3600.0 * hourlyWage);
  }
}
