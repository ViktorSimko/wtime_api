package com.viktorsimko.wtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * This class represents a resource for the API.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @JsonIgnore
  @Column(name = "user")
  private String user;

  /**
   * The id of the resource.
   *
   * @return the id of the resource
   */
  public int getId() {
    return id;
  }

  /**
   * The owner of the resource.
   *
   * @return the owner of the resource
   */
  public String getUser() {
    return user;
  }

  /**
   * Sets the user for the resource.
   *
   * @param user The name of the user to set for this resource.
   */
  public void setUser(String user) {
    this.user = user;
  }

}
