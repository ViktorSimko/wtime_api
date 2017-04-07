package com.viktorsimko.wtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The project class, it represents a project which a user works on.
 */
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "project_id", unique = true, nullable = false)
  private int id;

  @Column(name = "title")
  private String title;

  @JsonIgnore
  @Column(name = "user")
  private String user;

  /**
   * The id of the project.
   */
  public int getId() {
    return id;
  }

  /**
   * The title of the project.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the project.
   *
   * @param title The title to set for the project
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * The owner of the project.
   */
  public String getUser() {
    return user;
  }

  /**
   * Sets the user for the project.
   *
   * @param user The name of the user to set for this project.
   */
  public void setUser(String user) {
    this.user = user;
  }
}
