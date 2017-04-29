package com.viktorsimko.wtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This class represents a resource for the API.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Resource {

  @NotNull
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id", unique = true)
  private Integer id;

  @NotNull
  @JsonIgnore
  @Column(name = "user")
  private String user;

  /**
   * The id of the resource.
   *
   * @return the id of the resource
   */
  public Integer getId() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Resource)) return false;
    Resource resource = (Resource) o;
    return id == resource.id &&
      Objects.equals(user, resource.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user);
  }
}
