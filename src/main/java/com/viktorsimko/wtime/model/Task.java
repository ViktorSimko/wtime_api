package com.viktorsimko.wtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by simkoviktor on 2017. 03. 19.
 *
 * The task object for storing tasks related to a project
 */
@Entity
@Table(name = "task")
public class Task {

  /**
   * The id of the task
   */
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * The id of the project which this task belongs to
   */
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  /**
   * The title of the project
   */
  @Column(name = "title")
  private String title;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
