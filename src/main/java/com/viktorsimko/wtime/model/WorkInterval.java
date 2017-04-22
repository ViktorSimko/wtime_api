package com.viktorsimko.wtime.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents an interval of work done by the user on a given task.
 */
@Entity
@Table(name = "work_interval")
public class WorkInterval extends Resource {
}
