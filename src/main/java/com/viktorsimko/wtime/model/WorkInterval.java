package com.viktorsimko.wtime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * This class represents an interval of work done by the user on a given task.
 */
@Entity
@Table(name = "work_interval")
public class WorkInterval extends Resource {

  @Column(name = "task_id", nullable = false)
  private int taskId = -1;

  @Column(name = "begin")
  private LocalDateTime begin;

  @Column(name = "end")
  private LocalDateTime end;

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getTaskId() {
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
}
