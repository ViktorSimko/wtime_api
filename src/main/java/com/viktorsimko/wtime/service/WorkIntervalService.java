package com.viktorsimko.wtime.service;

import com.viktorsimko.wtime.model.WorkInterval;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * A service for managing and getting information about {@code WorkTimeInterval} objects.
 */
public interface WorkIntervalService extends ResourceService<WorkInterval> {

  List<WorkInterval> getWorkIntervals(String userName, int taskId);

  Duration allWorkedTimeForTask(String userName, int taskId);
}
