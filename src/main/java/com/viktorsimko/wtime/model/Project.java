package com.viktorsimko.wtime.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.Duration;
import java.util.Objects;

/**
 * The project class, it represents a project which a user works on.
 */
@Entity
@Table(name = "project")
public class Project extends Resource {

  @Column(name = "title")
  @Length(min = 1, message = "the title needs to be at least 1 character long ")
  private String title;

  @Column(name = "description")
  @Length(min = 1, message = "the description needs to be at least 1 character long ")
  private String description;

  @Column(name = "hourly_wage")
  private int hourlyWage = -1;

  @Transient
  private int allIncome;

  @Transient
  private Duration allWorkedTime;

  /**
   * The title of the project.
   *
   * @return the title of the project
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
   * The description of the project.
   *
   * @return the description of the project
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the project.
   *
   * @param description The description to set for the project
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * The hourly wage of the project.
   *
   * @return the hourly wage of the project
   */
  public int getHourlyWage() {
    return hourlyWage;
  }

  /**
   * Sets the hourly wage of the project.
   *
   * @param hourlyWage the hourly wage to set for the project
   */
  public void setHourlyWage(int hourlyWage) {
    this.hourlyWage = hourlyWage;
  }

  /**
   * The total income from the project.
   *
   * @return the total income from the project
   */
  public int getAllIncome() {
    return allIncome;
  }

  /**
   * Sets total income of the project.
   *
   * @param allIncome the hourly wage to set for the project
   */
  public void setAllIncome(int allIncome) {
    this.allIncome = allIncome;
  }

  /**
   * The total time worked on the project.
   *
   * @return the total time worked on the project
   */
  public Duration getAllWorkedTime() {
    return allWorkedTime;
  }

  /**
   * Sets total time worked on the project.
   *
   * @param allWorkedTime total time worked on the project
   */
  public void setAllWorkedTime(Duration allWorkedTime) {
    this.allWorkedTime = allWorkedTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Project)) return false;
    if (!super.equals(o)) return false;
    Project project = (Project) o;
    return hourlyWage == project.hourlyWage &&
      Objects.equals(title, project.title) &&
      Objects.equals(description, project.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), title, description, hourlyWage);
  }
}
