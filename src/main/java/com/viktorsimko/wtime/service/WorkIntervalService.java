package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.WorkInterval;

import java.time.Duration;
import java.util.Collection;

/**
 * A service for managing and getting information about {@code WorkTimeInterval} objects.
 */
public interface WorkIntervalService extends ResourceService<WorkInterval> {

  Collection<WorkInterval> getWorkIntervals(String userName, int taskId);

  Duration allWorkedTimeForTask(String userName, int taskId);
}
