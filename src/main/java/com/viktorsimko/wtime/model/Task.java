package com.viktorsimko.wtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * This class represents tasks.
 * Every task is associated with a {@code Project}.
 */
@Entity
@Table(name = "task")
public class Task extends Resource {

  @Column(name = "project_id", unique = true, nullable = false)
  private int projectId = -1;

  @Column(name = "title")
  private String title;

  /**
   * The id of the project which this task belongs to.
   *
   * @return the id of the owner project
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * Sets the id of the owner project.
   *
   * @param projectId the id of the new owner project
   */
  public void setProjectId(int projectId) {
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
}
