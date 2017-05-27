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

  /**
   * Sets the {@code taskId} of the {@code WorkInterval}.
   *
   * @param taskId the task id
   */
  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  /**
   * Returns the {@code taskId}.
   *
   * @return the task id to set
   */
  public Integer getTaskId() {
    return taskId;
  }

  /**
   * Returns the beginning date.
   *
   * @return the beginning date
   */
  public LocalDateTime getBegin() {
    return begin;
  }

  /**
   * Sets the beginning date.
   *
   * @param begin the beginning date to set
   */
  public void setBegin(LocalDateTime begin) {
    this.begin = begin;
  }

  /**
   * Returns the end date.
   *
   * @return the end date
   */
  public LocalDateTime getEnd() {
    return end;
  }

  /**
   * Sets the end date.
   *
   * @param end the end date to set
   */
  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  /**
   * Returns the duration between {@code begin} and {@code end}.
   * If {@code begin} or {@code end} is null, returns {@code Duration.ZERO}.
   *
   * @return the duration of the work interval
   */
  public Duration getDuration() {
    return (begin == null || end == null) ? Duration.ZERO : Duration.ofSeconds(ChronoUnit.SECONDS.between(begin, end));
  }
}
