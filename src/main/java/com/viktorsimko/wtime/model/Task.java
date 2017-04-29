package com.viktorsimko.wtime.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;

/**
 * This class represents tasks.
 * Every task is associated with a {@code Project}.
 */
@Entity
@Table(name = "task")
public class Task extends Resource {

  @NotNull
  @Column(name = "project_id")
  private Integer projectId;

  @NotNull
  @Column(name = "title")
  private String title;

  @NotNull
  @Column(name = "bonus")
  private Integer bonus;

  @Transient
  private Integer allIncome;

  @Transient
  private Duration allWorkedTime;

  /**
   * The id of the project which this task belongs to.
   *
   * @return the id of the owner project
   */
  public Integer getProjectId() {
    return projectId;
  }

  /**
   * Sets the id of the owner project.
   *
   * @param projectId the id of the new owner project
   */
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  /**
   * The title of the task.
   *
   * @return the title of the task
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the task.
   *
   * @param title the new title for the task
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * The bonus of the task.
   *
   * @return the bonus of the task
   */
  public Integer getBonus() {
    return bonus;
  }

  /**
   * Sets the bonus of the task.
   *
   * @param bonus the new bonus for the task
   */
  public void setBonus(Integer bonus) {
    this.bonus = bonus;
  }

  /**
   * The total income from the task.
   *
   * @return the total income from the task
   */
  public Integer getAllIncome() {
    return allIncome;
  }

  /**
   * Sets total income of the task.
   *
   * @param allIncome the hourly wage to set for the task
   */
  public void setAllIncome(Integer allIncome) {
    this.allIncome = allIncome;
  }

  /**
   * The total time worked on the task.
   *
   * @return the total time worked on the task
   */
  public Duration getAllWorkedTime() {
    return allWorkedTime;
  }

  /**
   * Sets total time worked on the task.
   *
   * @param allWorkedTime the total time worked on the task
   */
  public void setAllWorkedTime(Duration allWorkedTime) {
    this.allWorkedTime = allWorkedTime;
  }
}
