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
public class Project extends Resource {

  @Column(name = "title")
  private String title;

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
}
